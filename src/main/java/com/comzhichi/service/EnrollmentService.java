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

        // Descontar vacante
        section.setAvailableVacancies(section.getAvailableVacancies() - 1);
        sectionRepository.save(section);

        Enrollment enrollment = enrollmentMapper.toEntity(dto);
        enrollment.setStudent(student);
        enrollment.setSection(section);

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional
    public EnrollmentResponseDTO update(Long id, EnrollmentRequestDTO dto) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula no encontrada con ID: " + id));

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