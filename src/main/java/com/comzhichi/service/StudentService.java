package com.comzhichi.service;

import com.comzhichi.dto.request.StudentRequestDTO;
import com.comzhichi.dto.response.StudentResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.StudentMapper;
import com.comzhichi.model.Student;
import com.comzhichi.model.User;
import com.comzhichi.repository.StudentRepository;
import com.comzhichi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.comzhichi.config.CurrentUser;

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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estudiante no encontrado con ID: " + id));

        boolean isCoordinator = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_COORDINATOR"));

        if (!isCoordinator && !student.getUser().getId().equals(CurrentUser.getUserId())) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "No tienes permiso para consultar este estudiante");
        }

        return studentMapper.toResponse(student);
    }
    @Transactional
    public StudentResponseDTO create(StudentRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.userId()));

        Student student = studentMapper.toEntity(dto);
        student.setUser(user);

        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));
        studentRepository.delete(student);
    }
}