package com.comzhichi.service;

import com.comzhichi.dto.request.StudentRequestDTO;
import com.comzhichi.dto.response.StudentResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ConflictException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.StudentMapper;
import com.comzhichi.model.Role;
import com.comzhichi.model.Student;
import com.comzhichi.model.User;
import com.comzhichi.repository.StudentRepository;
import com.comzhichi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));
        return studentMapper.toResponse(student);
    }

    @Transactional
    public StudentResponseDTO create(StudentRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.userId()));

        if (user.getRole() != Role.STUDENT)
            throw new BusinessRuleException("El usuario no tiene rol STUDENT");
        if (studentRepository.existsByUser_Id(user.getId()))
            throw new ConflictException("El usuario ya tiene un perfil de estudiante");

        Student student = studentMapper.toEntity(dto);
        student.setUser(user);

        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO findByUserId(Long userId) {
        return studentRepository.findByUser_Id(userId)
                .map(studentMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de estudiante no encontrado para el usuario: " + userId));
    }

    @Transactional
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));
        studentRepository.delete(student);
    }
}