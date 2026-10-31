package com.comzhichi.service;

import com.comzhichi.dto.request.SectionRequestDTO;
import com.comzhichi.dto.response.SectionResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.SectionMapper;
import com.comzhichi.model.Course;
import com.comzhichi.model.Section;
import com.comzhichi.model.Teacher;
import com.comzhichi.repository.CourseRepository;
import com.comzhichi.repository.SectionRepository;
import com.comzhichi.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final SectionMapper sectionMapper;

    @Transactional(readOnly = true)
    public List<SectionResponseDTO> findAll(Long courseId, Boolean available, String dayOfWeek) {
        return sectionRepository.findAll().stream()
                .filter(s -> courseId == null || s.getCourse().getId().equals(courseId))
                .filter(s -> !Boolean.TRUE.equals(available) || s.getAvailableVacancies() > 0)
                .filter(s -> dayOfWeek == null || dayOfWeek.isBlank()
                        || s.getDayOfWeek().toLowerCase().contains(dayOfWeek.toLowerCase()))
                .map(sectionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SectionResponseDTO findById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + id));
        return sectionMapper.toResponse(section);
    }

    @Transactional
    public SectionResponseDTO create(SectionRequestDTO dto) {
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + dto.courseId()));

        Section section = sectionMapper.toEntity(dto);
        section.setCourse(course);

        if (dto.teacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + dto.teacherId()));
            section.setTeacher(teacher);
            validateTeacherAssignment(teacher, course, section, null);
        }

        return sectionMapper.toResponse(sectionRepository.save(section));
    }

    @Transactional
    public SectionResponseDTO update(Long id, SectionRequestDTO dto) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + id));

        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + dto.courseId()));

        section.setGroupCode(dto.groupCode());
        section.setDayOfWeek(dto.dayOfWeek());
        section.setStartTime(dto.startTime());
        section.setEndTime(dto.endTime());
        section.setClassroom(dto.classroom());
        section.setTotalVacancies(dto.totalVacancies());
        section.setAvailableVacancies(dto.availableVacancies());
        section.setCourse(course);

        Teacher teacher = null;
        if (dto.teacherId() != null) {
            teacher = teacherRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + dto.teacherId()));
        }
        section.setTeacher(teacher);

        if (teacher != null) {
            validateTeacherAssignment(teacher, course, section, id);
        }

        return sectionMapper.toResponse(sectionRepository.save(section));
    }

    private void validateTeacherAssignment(Teacher teacher, Course course, Section candidate, Long excludeSectionId) {
        if (!isSpecialtyCompatible(teacher.getSpecialty(), course.getLanguage())) {
            throw new BusinessRuleException("El docente no tiene especialidad compatible con el idioma del curso");
        }

        List<Section> existingSections = sectionRepository.findByTeacherId(teacher.getId());
        for (Section existing : existingSections) {
            if (excludeSectionId != null && existing.getId().equals(excludeSectionId)) {
                continue;
            }
            if (sectionsOverlap(existing, candidate)) {
                throw new BusinessRuleException("El docente no está disponible en ese horario");
            }
        }
    }

    private boolean isSpecialtyCompatible(String specialty, String language) {
        if (specialty == null || specialty.isBlank() || language == null || language.isBlank()) {
            return false;
        }
        String s = specialty.trim().toLowerCase();
        String l = language.trim().toLowerCase();
        return s.contains(l) || l.contains(s);
    }

    private boolean sectionsOverlap(Section a, Section b) {
        Set<String> daysA = parseDays(a.getDayOfWeek());
        Set<String> daysB = parseDays(b.getDayOfWeek());
        boolean sameDay = daysA.stream().anyMatch(daysB::contains);
        if (!sameDay) {
            return false;
        }

        LocalTime startA = parseTime(a.getStartTime());
        LocalTime endA = parseTime(a.getEndTime());
        LocalTime startB = parseTime(b.getStartTime());
        LocalTime endB = parseTime(b.getEndTime());

        return startA.isBefore(endB) && startB.isBefore(endA);
    }

    private Set<String> parseDays(String value) {
        if (value == null || value.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .collect(Collectors.toSet());
    }

    private LocalTime parseTime(String value) {
        return LocalTime.parse(value.trim(), DateTimeFormatter.ofPattern("H:mm"));
    }

    @Transactional
    public void delete(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + id));
        sectionRepository.delete(section);
    }
}