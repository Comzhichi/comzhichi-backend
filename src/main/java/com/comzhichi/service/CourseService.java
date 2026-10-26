package com.comzhichi.service;

import com.comzhichi.dto.request.CourseRequestDTO;
import com.comzhichi.dto.response.CourseResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.CourseMapper;
import com.comzhichi.model.Coordinator;
import com.comzhichi.model.Course;
import com.comzhichi.repository.CoordinatorRepository;
import com.comzhichi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> findAll(String name, String language, String level) {
        return courseRepository.findAll().stream()
                .filter(c -> name == null || name.isBlank()
                        || c.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(c -> language == null || language.isBlank()
                        || c.getLanguage().toLowerCase().contains(language.toLowerCase()))
                .filter(c -> level == null || level.isBlank()
                        || c.getLevel().toLowerCase().contains(level.toLowerCase()))
                .map(courseMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseResponseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        return courseMapper.toResponse(course);
    }

    @Transactional
    public CourseResponseDTO create(CourseRequestDTO dto) {
        Course course = courseMapper.toEntity(dto);

        if (dto.coordinatorId() != null) {
            Coordinator coordinator = coordinatorRepository.findById(dto.coordinatorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coordinador no encontrado con ID: " + dto.coordinatorId()));
            course.setCoordinator(coordinator);
        }

        if (dto.prerequisiteId() != null) {
            Course prerequisite = courseRepository.findById(dto.prerequisiteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Prerequisito no encontrado con ID: " + dto.prerequisiteId()));
            course.setPrerequisite(prerequisite);
        }

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Transactional
    public CourseResponseDTO update(Long id, CourseRequestDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));

        course.setName(dto.name());
        course.setLanguage(dto.language());
        course.setLevel(dto.level());
        course.setDescription(dto.description());
        course.setCoordinator(dto.coordinatorId() != null
                ? coordinatorRepository.findById(dto.coordinatorId())
                .orElseThrow(() -> new ResourceNotFoundException("Coordinador no encontrado"))
                : null);
        course.setPrerequisite(dto.prerequisiteId() != null
                ? courseRepository.findById(dto.prerequisiteId())
                .orElseThrow(() -> new ResourceNotFoundException("Prerequisito no encontrado"))
                : null);

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Transactional
    public void delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        courseRepository.delete(course);
    }
}