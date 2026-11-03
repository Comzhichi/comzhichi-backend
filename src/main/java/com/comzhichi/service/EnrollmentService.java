package com.comzhichi.service;

import com.comzhichi.dto.request.EnrollmentRequestDTO;
import com.comzhichi.dto.response.EnrollmentResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.config.CurrentUser;
import com.comzhichi.mapper.EnrollmentMapper;
import com.comzhichi.security.UserPrincipal;
import com.comzhichi.model.*;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.LevelEvaluationRepository;
import com.comzhichi.repository.SectionRepository;
import com.comzhichi.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final LevelEvaluationRepository levelEvaluationRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                            StudentRepository studentRepository,
                            SectionRepository sectionRepository,
                            EnrollmentMapper enrollmentMapper) {
        this(enrollmentRepository, studentRepository, sectionRepository, enrollmentMapper, null);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                            StudentRepository studentRepository,
                            SectionRepository sectionRepository,
                            EnrollmentMapper enrollmentMapper,
                            LevelEvaluationRepository levelEvaluationRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.enrollmentMapper = enrollmentMapper;
        this.levelEvaluationRepository = levelEvaluationRepository;
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> findAll(Long studentId, Long sectionId) {
        List<Enrollment> enrollments;
        if (sectionId != null) {
            enrollments = enrollmentRepository.findBySectionId(sectionId);
            if (studentId != null) {
                enrollments = enrollments.stream()
                        .filter(e -> e.getStudent() != null && studentId.equals(e.getStudent().getId()))
                        .toList();
            }
        } else if (studentId != null) {
            enrollments = enrollmentRepository.findByStudentId(studentId);
        } else {
            enrollments = enrollmentRepository.findAll();
        }
        return enrollments.stream().map(enrollmentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> findBySectionId(Long sectionId) {
        return enrollmentRepository.findBySectionId(sectionId).stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EnrollmentResponseDTO findById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));
        return enrollmentMapper.toResponse(enrollment);
    }

    @Transactional
    public EnrollmentResponseDTO create(EnrollmentRequestDTO dto) {
        // Si el usuario autenticado es un ESTUDIANTE, la matrícula se hace
        // para sí mismo: se ignora el studentId del body (evita suplantación).
        Long effectiveStudentId = dto.studentId();
        UserPrincipal principal = CurrentUser.getPrincipal();
        if (principal != null && principal.getUser().getRole() == Role.STUDENT) {
            Student self = studentRepository.findByUser_Id(principal.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Perfil de estudiante no encontrado para el usuario autenticado"));
            effectiveStudentId = self.getId();
        }

        final Long studentIdToUse = effectiveStudentId;

        Student student = studentRepository.findById(studentIdToUse)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + studentIdToUse));

        Section newSection = sectionRepository.findById(dto.sectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + dto.sectionId()));

        if (newSection.getAvailableVacancies() <= 0)
            throw new BusinessRuleException("No hay vacantes disponibles en la sección indicada");

        // Verificar prerequisito
        Course requiredCourse = newSection.getCourse().getPrerequisite();
        if (requiredCourse != null) {
            List<Enrollment> studentEnrollments = enrollmentRepository.findByStudentId(student.getId());
            boolean approved = studentEnrollments.stream()
                    .anyMatch(e -> coursesMatch(e.getSection().getCourse(), requiredCourse)
                            && isPrerequisiteApproved(e));

            // Sincronizar (reparar) el estado de las matrículas aprobadas que
            // aún figuren como ACTIVE, de modo que validaciones futuras pasen.
            if (approved) {
                studentEnrollments.stream()
                        .filter(e -> coursesMatch(e.getSection().getCourse(), requiredCourse))
                        .filter(e -> !EnrollmentStatus.APPROVED.equals(e.getStatus()))
                        .filter(this::hasApprovedLevelEvaluation)
                        .forEach(e -> {
                            e.setStatus(EnrollmentStatus.APPROVED);
                            enrollmentRepository.save(e);
                        });
            }

            if (!approved)
                throw new BusinessRuleException("El estudiante no ha aprobado el prerequisito: "
                        + requiredCourse.getName());
        }

        // Verificar cruce de horarios
        boolean overlap = enrollmentRepository.findByStudentId(student.getId()).stream()
                .filter(e -> EnrollmentStatus.ACTIVE.equals(e.getStatus()))
                .anyMatch(e -> schedulesOverlap(newSection, e.getSection()));
        if (overlap)
            throw new BusinessRuleException("El horario se cruza con una matrícula activa");

        newSection.setAvailableVacancies(newSection.getAvailableVacancies() - 1);
        sectionRepository.save(newSection);

        Enrollment enrollment = enrollmentMapper.toEntity(dto);
        enrollment.setStudent(student);
        enrollment.setSection(newSection);

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public EnrollmentResponseDTO update(Long id, EnrollmentRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));

        if (!enrollment.getSection().getId().equals(dto.sectionId())) {
            Section newSection = sectionRepository.findById(dto.sectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + dto.sectionId()));

            if (newSection.getAvailableVacancies() <= 0)
                throw new BusinessRuleException("No hay vacantes disponibles en la nueva sección");

            boolean overlap = enrollmentRepository.findByStudentId(enrollment.getStudent().getId()).stream()
                    .filter(e -> !e.getId().equals(id) && EnrollmentStatus.ACTIVE.equals(e.getStatus()))
                    .anyMatch(e -> schedulesOverlap(newSection, e.getSection()));

            enrollment.getSection().setAvailableVacancies(enrollment.getSection().getAvailableVacancies() + 1);
            newSection.setAvailableVacancies(newSection.getAvailableVacancies() - 1);
            sectionRepository.save(enrollment.getSection());
            sectionRepository.save(newSection);
            enrollment.setSection(newSection);
        }

        enrollment.setStatus(dto.status());
        enrollment.setEnrollmentDate(dto.enrollmentDate());

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public void delete(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));

        Section section = enrollment.getSection();
        section.setAvailableVacancies(section.getAvailableVacancies() + 1);
        sectionRepository.save(section);

        enrollmentRepository.delete(enrollment);
    }

    private boolean coursesMatch(Course a, Course b) {
        if (a == null || b == null) return false;
        if (a.getId() != null && a.getId().equals(b.getId())) return true;
        return a.getName() != null && a.getName().equalsIgnoreCase(b.getName());
    }

    private boolean hasApprovedLevelEvaluation(Enrollment enrollment) {
        if (levelEvaluationRepository == null || enrollment == null || enrollment.getId() == null)
            return false;
        return levelEvaluationRepository.findByEnrollmentId(enrollment.getId()).stream()
                .anyMatch(ev -> ev.getResult() == LevelEvaluationResult.APPROVED);
    }

    private boolean isPrerequisiteApproved(Enrollment enrollment) {
        if (EnrollmentStatus.APPROVED.equals(enrollment.getStatus()))
            return true;
        return hasApprovedLevelEvaluation(enrollment);
    }

    // --- Lógica de cruce de horarios con dayOfWeek, startTime, endTime ---
    private boolean schedulesOverlap(Section a, Section b) {
        Set<String> daysA = Arrays.stream(a.getDayOfWeek().split(","))
                .map(String::trim).map(String::toUpperCase).collect(Collectors.toSet());
        Set<String> daysB = Arrays.stream(b.getDayOfWeek().split(","))
                .map(String::trim).map(String::toUpperCase).collect(Collectors.toSet());

        // Si no comparten ningún día, no hay cruce
        daysA.retainAll(daysB);
        if (daysA.isEmpty()) return false;

        int startA = timeToMinutes(a.getStartTime());
        int endA   = timeToMinutes(a.getEndTime());
        int startB = timeToMinutes(b.getStartTime());
        int endB   = timeToMinutes(b.getEndTime());

        return startA < endB && startB < endA;
    }

    private int timeToMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    @Transactional
    public EnrollmentResponseDTO drop(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));

        if (enrollment.getStatus() == EnrollmentStatus.DROPPED) {
            throw new BusinessRuleException("La matrícula ya fue dada de baja anteriormente");
        }

        // Devolver la vacante a la sección
        Section section = enrollment.getSection();
        section.setAvailableVacancies(section.getAvailableVacancies() + 1);
        sectionRepository.save(section);

        enrollment.setStatus(EnrollmentStatus.DROPPED);
        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public EnrollmentResponseDTO transfer(Long enrollmentId, Long newSectionId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + enrollmentId));

        if (enrollment.getStatus() == EnrollmentStatus.DROPPED) {
            throw new BusinessRuleException("No se puede transferir una matrícula dada de baja");
        }

        Section newSection = sectionRepository.findById(newSectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + newSectionId));

        // Validar que sea el mismo curso
        if (!enrollment.getSection().getCourse().getId().equals(newSection.getCourse().getId())) {
            throw new BusinessRuleException("Solo se puede transferir a una sección del mismo curso");
        }

        // Validar que no sea la misma sección
        if (enrollment.getSection().getId().equals(newSectionId)) {
            throw new BusinessRuleException("El estudiante ya está matriculado en esa sección");
        }

        // Validar vacantes
        if (newSection.getAvailableVacancies() <= 0) {
            throw new BusinessRuleException("No hay vacantes disponibles en la sección destino");
        }

        // Validar cruce de horario con otras matrículas activas (excluyendo la actual)
        boolean overlap = enrollmentRepository.findByStudentId(enrollment.getStudent().getId()).stream()
                .filter(e -> !e.getId().equals(enrollmentId))
                .filter(e -> EnrollmentStatus.ACTIVE.equals(e.getStatus()))
                .anyMatch(e -> schedulesOverlap(newSection, e.getSection()));

        if (overlap) {
            throw new BusinessRuleException("El horario de la sección destino se cruza con otra matrícula activa");
        }

        // Devolver vacante a sección anterior
        Section oldSection = enrollment.getSection();
        oldSection.setAvailableVacancies(oldSection.getAvailableVacancies() + 1);
        sectionRepository.save(oldSection);

        // Descontar vacante en sección nueva
        newSection.setAvailableVacancies(newSection.getAvailableVacancies() - 1);
        sectionRepository.save(newSection);

        enrollment.setSection(newSection);
        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }
}