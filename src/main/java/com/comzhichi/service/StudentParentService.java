package com.comzhichi.service;

import com.comzhichi.dto.request.StudentParentRequestDTO;
import com.comzhichi.dto.response.StudentParentResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.StudentParentMapper;
import com.comzhichi.model.Parent;
import com.comzhichi.model.Student;
import com.comzhichi.model.StudentParent;
import com.comzhichi.repository.ParentRepository;
import com.comzhichi.repository.StudentParentRepository;
import com.comzhichi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentParentService {

    private final StudentParentRepository studentParentRepository;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final StudentParentMapper studentParentMapper;

    @Transactional(readOnly = true)
    public List<StudentParentResponseDTO> findAll() {
        return studentParentRepository.findAll().stream()
                .map(studentParentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentParentResponseDTO findById(Long id) {
        StudentParent sp = studentParentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relación Estudiante-Apoderado no encontrada con ID: " + id));
        return studentParentMapper.toResponse(sp);
    }

    @Transactional
    public StudentParentResponseDTO create(StudentParentRequestDTO dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + dto.studentId()));

        Parent parent = parentRepository.findById(dto.parentId())
                .orElseThrow(() -> new ResourceNotFoundException("Apoderado no encontrado con ID: " + dto.parentId()));

        StudentParent studentParent = studentParentMapper.toEntity(dto);
        studentParent.setStudent(student);
        studentParent.setParent(parent);

        return studentParentMapper.toResponse(studentParentRepository.save(studentParent));
    }

    @Transactional
    public StudentParentResponseDTO update(Long id, StudentParentRequestDTO dto) {
        StudentParent studentParent = studentParentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relación Estudiante-Apoderado no encontrada con ID: " + id));

        studentParent.setRelationship(dto.relationship());
        studentParent.setCanEnroll(dto.canEnroll() != null ? dto.canEnroll() : false);

        return studentParentMapper.toResponse(studentParentRepository.save(studentParent));
    }

    @Transactional
    public void delete(Long id) {
        StudentParent studentParent = studentParentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relación Estudiante-Apoderado no encontrada con ID: " + id));
        studentParentRepository.delete(studentParent);
    }
}