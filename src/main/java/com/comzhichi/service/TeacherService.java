package com.comzhichi.service;

import com.comzhichi.dto.request.TeacherRequestDTO;
import com.comzhichi.dto.response.TeacherResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ConflictException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.TeacherMapper;
import com.comzhichi.model.Role;
import com.comzhichi.model.Teacher;
import com.comzhichi.model.User;
import com.comzhichi.repository.TeacherRepository;
import com.comzhichi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TeacherMapper teacherMapper;

    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> findAll(String specialty) {
        List<Teacher> teachers = (specialty == null || specialty.isBlank())
                ? teacherRepository.findAll()
                : teacherRepository.findBySpecialtyContainingIgnoreCase(specialty);
        return teachers.stream().map(teacherMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TeacherResponseDTO findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + id));
        return teacherMapper.toResponse(teacher);
    }

    @Transactional
    public TeacherResponseDTO create(TeacherRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.userId()));

        if (user.getRole() != Role.TEACHER)
            throw new BusinessRuleException("El usuario no tiene rol TEACHER");
        if (teacherRepository.existsByUser_Id(user.getId()))
            throw new ConflictException("El usuario ya tiene un perfil de docente");

        Teacher teacher = teacherMapper.toEntity(dto);
        teacher.setUser(user);
        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }

    @Transactional(readOnly = true)
    public TeacherResponseDTO findByUserId(Long userId) {
        return teacherRepository.findByUser_Id(userId)
                .map(teacherMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de docente no encontrado para el usuario: " + userId));
    }

    @Transactional
    public TeacherResponseDTO update(Long id, TeacherRequestDTO dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + id));
        teacher.setSpecialty(dto.specialty());
        return teacherMapper.toResponse(teacherRepository.save(teacher));
    }

    @Transactional
    public void delete(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + id));
        teacherRepository.delete(teacher);
    }
}