package com.comzhichi.service;

import com.comzhichi.dto.request.TeacherRequestDTO;
import com.comzhichi.dto.response.TeacherResponseDTO;
import com.comzhichi.exception.BusinessRuleException;
import com.comzhichi.exception.ConflictException;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.TeacherMapper;
import com.comzhichi.model.Course;
import com.comzhichi.model.Role;
import com.comzhichi.model.Section;
import com.comzhichi.model.Teacher;
import com.comzhichi.model.User;
import com.comzhichi.repository.CourseRepository;
import com.comzhichi.repository.SectionRepository;
import com.comzhichi.repository.TeacherRepository;
import com.comzhichi.repository.UserRepository;
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
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final TeacherMapper teacherMapper;

    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> findAll(String specialty) {
        List<Teacher> teachers = (specialty == null || specialty.isBlank())
                ? teacherRepository.findAll()
                : teacherRepository.findBySpecialtyContainingIgnoreCase(specialty);
        return teachers.stream().map(teacherMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> findEligible(Long courseId, String dayOfWeek, String startTime, String endTime) {
        Course course = null;
        if (courseId != null) {
            course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con ID: " + courseId));
        }

        Section candidate = null;
        if (dayOfWeek != null && !dayOfWeek.isBlank() && startTime != null && !startTime.isBlank()
                && endTime != null && !endTime.isBlank()) {
            candidate = new Section();
            candidate.setDayOfWeek(dayOfWeek);
            candidate.setStartTime(startTime);
            candidate.setEndTime(endTime);
            if (course != null) {
                candidate.setCourse(course);
            }
        }

        final Course finalCourse = course;
        final Section finalCandidate = candidate;

        return teacherRepository.findAll().stream()
                .filter(t -> finalCourse == null || isSpecialtyCompatible(t.getSpecialty(), finalCourse.getLanguage()))
                .filter(t -> finalCandidate == null || sectionRepository.findByTeacherId(t.getId()).stream()
                        .noneMatch(s -> sectionsOverlap(s, finalCandidate)))
                .map(teacherMapper::toResponse)
                .toList();
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
        if (daysA.stream().noneMatch(daysB::contains)) {
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