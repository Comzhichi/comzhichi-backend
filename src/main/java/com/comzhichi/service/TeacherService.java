package com.comzhichi.service;

import com.comzhichi.model.Teacher;
import com.comzhichi.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> findAll(String specialty) {
        if (specialty == null || specialty.isBlank()) {
            return teacherRepository.findAll();
        }
        return teacherRepository.findBySpecialtyContainingIgnoreCase(specialty);
    }

    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con ID: " + id));
    }

    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(Long id, Teacher datos) {
        Teacher teacher = findById(id);
        teacher.setSpecialty(datos.getSpecialty());
        return teacherRepository.save(teacher);
    }

    public void delete(Long id) {
        Teacher teacher = findById(id);
        teacherRepository.delete(teacher);
    }
}