package com.comzhichi.service;

import com.comzhichi.dto.request.EnrollmentRequestDTO;
import com.comzhichi.dto.response.EnrollmentResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.EnrollmentMapper;
import com.comzhichi.model.Enrollment;
import com.comzhichi.model.Section;
import com.comzhichi.model.Student;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.SectionRepository;
import com.comzhichi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        return enrollments.stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<EnrollmentResponseDTO> findBySectionId(Long sectionId) {
        return enrollmentRepository.findBySectionId(sectionId)
                .stream()
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

        Section section = sectionRepository.findById(dto.sectionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + dto.sectionId()));

        if (section.getAvailableVacancies() <= 0) {
            throw new BusinessRuleException("No hay vacantes disponibles en la sección indicada");
        }

        if (isActive(dto.status()) && enrollmentRepository.findByStudentId(student.getId()).stream()
                .filter(enrollment -> isActive(enrollment.getStatus()))
                .anyMatch(enrollment -> schedulesOverlap(
                        section.getSchedule(), enrollment.getSection().getSchedule()))) {
            throw new BusinessRuleException("El horario se cruza con una matrícula activa");
        }

        // Descontar vacante
        section.setAvailableVacancies(section.getAvailableVacancies() - 1);
        sectionRepository.save(section);

        Enrollment enrollment = enrollmentMapper.toEntity(dto);
        enrollment.setStudent(student);
        enrollment.setSection(section);

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    private boolean isActive(String status) {
        return status != null && (status.equalsIgnoreCase("ACTIVA")
                || status.equalsIgnoreCase("ACTIVE"));
    }

    private boolean schedulesOverlap(String first, String second) {
        String a = first.toLowerCase();
        String b = second.toLowerCase();
        String[] days = {"lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"};
        boolean sameDay = true;
        boolean hasDay = false;
        for (String day : days) {
            if (a.contains(day) || b.contains(day)) {
                hasDay = true;
                sameDay = sameDay && a.contains(day) && b.contains(day);
            }
        }
        if (!sameDay || !hasDay) {
            return false;
        }

        Matcher firstTime = Pattern.compile("(\\d{1,2}):(\\d{2})\\s*-\\s*(\\d{1,2}):(\\d{2})").matcher(a);
        Matcher secondTime = Pattern.compile("(\\d{1,2}):(\\d{2})\\s*-\\s*(\\d{1,2}):(\\d{2})").matcher(b);
        if (firstTime.find() && secondTime.find()) {
            int firstStart = Integer.parseInt(firstTime.group(1)) * 60 + Integer.parseInt(firstTime.group(2));
            int firstEnd = Integer.parseInt(firstTime.group(3)) * 60 + Integer.parseInt(firstTime.group(4));
            int secondStart = Integer.parseInt(secondTime.group(1)) * 60 + Integer.parseInt(secondTime.group(2));
            int secondEnd = Integer.parseInt(secondTime.group(3)) * 60 + Integer.parseInt(secondTime.group(4));
            return firstStart < secondEnd && secondStart < firstEnd;
        }
        return a.equals(b);
    }

    @Transactional
    public EnrollmentResponseDTO update(Long id, EnrollmentRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));

        if (!enrollment.getSection().getId().equals(dto.sectionId())) {
            Section newSection = sectionRepository.findById(dto.sectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + dto.sectionId()));
            if (newSection.getAvailableVacancies() <= 0) {
                throw new BusinessRuleException("No hay vacantes disponibles en la nueva sección");
            }
            if (isActive(dto.status()) && enrollmentRepository.findByStudentId(enrollment.getStudent().getId()).stream()
                    .filter(other -> !other.getId().equals(id) && isActive(other.getStatus()))
                    .anyMatch(other -> schedulesOverlap(newSection.getSchedule(), other.getSection().getSchedule()))) {
                throw new BusinessRuleException("El nuevo horario se cruza con una matrícula activa");
            }
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

        // Devolver la vacante a la sección
        Section section = enrollment.getSection();
        section.setAvailableVacancies(section.getAvailableVacancies() + 1);
        sectionRepository.save(section);

        enrollmentRepository.delete(enrollment);
    }
}