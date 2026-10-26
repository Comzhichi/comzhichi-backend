package com.comzhichi.service;

import com.comzhichi.dto.request.EnrollmentRequestDTO;
import com.comzhichi.dto.response.EnrollmentResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.EnrollmentMapper;
import com.comzhichi.model.*;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.SectionRepository;
import com.comzhichi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> findAll(Long studentId) {
        List<Enrollment> enrollments = (studentId == null)
                ? enrollmentRepository.findAll()
                : enrollmentRepository.findByStudentId(studentId);
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
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + dto.studentId()));

        Section newSection = sectionRepository.findById(dto.sectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + dto.sectionId()));

        if (newSection.getAvailableVacancies() <= 0)
            throw new BusinessRuleException("No hay vacantes disponibles en la sección indicada");

        // Verificar prerequisito
        Course requiredCourse = newSection.getCourse().getPrerequisite();
        if (requiredCourse != null) {
            boolean approved = enrollmentRepository.findByStudentId(student.getId()).stream()
                    .anyMatch(e -> e.getSection().getCourse().getId().equals(requiredCourse.getId())
                            && EnrollmentStatus.APPROVED.equals(e.getStatus()));
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
}