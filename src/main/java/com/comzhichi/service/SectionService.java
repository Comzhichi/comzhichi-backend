package com.comzhichi.service;

import com.comzhichi.dto.request.SectionRequestDTO;
import com.comzhichi.dto.response.SectionResponseDTO;
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

import java.util.List;

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
        section.setTeacher(dto.teacherId() != null
                ? teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado con ID: " + dto.teacherId()))
                : null);

        return sectionMapper.toResponse(sectionRepository.save(section));
    }

    @Transactional
    public void delete(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección no encontrada con ID: " + id));
        sectionRepository.delete(section);
    }
}