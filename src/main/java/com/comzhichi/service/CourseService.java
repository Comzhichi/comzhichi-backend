package com.comzhichi.service;

import com.comzhichi.dto.request.CourseRequestDTO;
import com.comzhichi.dto.response.CourseResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.CourseMapper;
import com.comzhichi.model.Coordinator;
import com.comzhichi.model.Course;
import com.comzhichi.repository.CoordinatorRepository;
import com.comzhichi.repository.CourseRepository;
import com.comzhichi.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CoordinatorRepository coordinatorRepository;
    private final SectionRepository sectionRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> findAll(String name, String level) {
        return findAll(name, level, null, null);
    }

    public List<CourseResponseDTO> findAll(String name) {
        return findAll(name, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> findAll(
            String name, String level, String schedule, Boolean available) {
        List<Course> courses;

        if (name != null && !name.isBlank() && (level == null || level.isBlank())) {
            courses = courseRepository.findByNameContainingIgnoreCase(name);
        } else if (level != null && !level.isBlank() && (name == null || name.isBlank())) {
            courses = courseRepository.findByLevelContainingIgnoreCase(level);
        } else {
            courses = courseRepository.findAll();
        }

        if (name != null && !name.isBlank()
                && (level != null && !level.isBlank())) {
            courses = courses.stream()
                    .filter(course -> course.getName().toLowerCase().contains(name.toLowerCase()))
                    .filter(course -> course.getLevel().toLowerCase().contains(level.toLowerCase()))
                    .toList();
        }

        if ((schedule != null && !schedule.isBlank()) || Boolean.TRUE.equals(available)) {
            String normalizedSchedule = schedule == null ? null : schedule.toLowerCase();
            Set<Long> matchingCourseIds = sectionRepository.findAll().stream()
                    .filter(section -> normalizedSchedule == null
                            || section.getSchedule().toLowerCase().contains(normalizedSchedule))
                    .filter(section -> !Boolean.TRUE.equals(available)
                            || section.getAvailableVacancies() > 0)
                    .map(section -> section.getCourse().getId())
                    .collect(Collectors.toSet());
            courses = courses.stream()
                    .filter(course -> matchingCourseIds.contains(course.getId()))
                    .toList();
        }

        return courses.stream()
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

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Transactional
    public CourseResponseDTO update(Long id, CourseRequestDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));

        course.setName(dto.name());
        course.setLevel(dto.level());
        course.setDescription(dto.description());

        if (dto.coordinatorId() != null) {
            Coordinator coordinator = coordinatorRepository.findById(dto.coordinatorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Coordinador no encontrado con ID: " + dto.coordinatorId()));
            course.setCoordinator(coordinator);
        } else {
            course.setCoordinator(null);
        }

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Transactional
    public void delete(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + id));
        courseRepository.delete(course);
    }
}