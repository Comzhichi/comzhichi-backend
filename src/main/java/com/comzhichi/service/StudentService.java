package com.comzhichi.service;

import com.comzhichi.dto.request.StudentRequestDTO;
import com.comzhichi.dto.response.StudentResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.StudentMapper;
import com.comzhichi.model.Role;
import com.comzhichi.model.Student;
import com.comzhichi.model.User;
import com.comzhichi.repository.StudentRepository;
import com.comzhichi.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> findAll(String level) {
        List<Student> students = (level == null || level.isBlank())
                ? studentRepository.findAll()
                : studentRepository.findByLevelContainingIgnoreCase(level);

        return students.stream()
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
        if (studentRepository.existsById(dto.userId())) {
            throw new BusinessRuleException("El estudiante ya se encuentra registrado con ID: " + dto.userId());
        }

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + dto.userId()));

        if (user.getRole() != Role.STUDENT) {
            throw new BusinessRuleException("El usuario debe tener el rol STUDENT para crear su perfil de estudiante");
        }

        studentRepository.insertStudent(dto.userId(), dto.level(), dto.institution());
        entityManager.flush();
        entityManager.clear();

        Student student = studentRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Error al recuperar el estudiante creado con ID: " + dto.userId()));

        return studentMapper.toResponse(student);
    }

    @Transactional
    public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));

        student.setLevel(dto.level());
        student.setInstitution(dto.institution());
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));
        studentRepository.delete(student);
    }
}
