package com.comzhichi.service;

import com.comzhichi.dto.request.CourseRequestDTO;
import com.comzhichi.dto.response.CourseResponseDTO;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.mapper.CourseMapper;
import com.comzhichi.model.Coordinator;
import com.comzhichi.model.Course;
import com.comzhichi.repository.CoordinatorRepository;
import com.comzhichi.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CoordinatorRepository coordinatorRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    @Test
    void findAllWithoutNameReturnsAllCourses() {
        Course course = createCourse(1L, "Inglés", "Básico");
        CourseResponseDTO response = createResponse(course);

        when(courseRepository.findAll()).thenReturn(List.of(course));
        when(courseMapper.toResponse(course)).thenReturn(response);

        List<CourseResponseDTO> result = courseService.findAll(null);

        assertEquals(List.of(response), result);
        verify(courseRepository).findAll();
        verify(courseMapper).toResponse(course);
        verifyNoInteractions(coordinatorRepository);
    }

    @Test
    void findAllWithNameUsesNameFilter() {
        Course course = createCourse(1L, "Inglés", "Básico");
        CourseResponseDTO response = createResponse(course);

        when(courseRepository.findByNameContainingIgnoreCase("inglés"))
                .thenReturn(List.of(course));
        when(courseMapper.toResponse(course)).thenReturn(response);

        List<CourseResponseDTO> result = courseService.findAll("inglés");

        assertEquals(List.of(response), result);
        verify(courseRepository)
                .findByNameContainingIgnoreCase("inglés");
    }

    @Test
    void findByIdReturnsCourseWhenItExists() {
        Course course = createCourse(1L, "Inglés", "Básico");
        CourseResponseDTO response = createResponse(course);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));
        when(courseMapper.toResponse(course))
                .thenReturn(response);

        CourseResponseDTO result = courseService.findById(1L);

        assertEquals(response, result);
    }

    @Test
    void findByIdThrowsExceptionWhenCourseDoesNotExist() {
        when(courseRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.findById(99L)
        );
    }

    @Test
    void createSavesCourseWithoutCoordinator() {
        CourseRequestDTO request = new CourseRequestDTO(
                "Inglés",
                "Básico",
                "Curso inicial",
                null
        );

        Course course = createCourse(1L, "Inglés", "Básico");
        CourseResponseDTO response = createResponse(course);

        when(courseMapper.toEntity(request))
                .thenReturn(course);
        when(courseRepository.save(course))
                .thenReturn(course);
        when(courseMapper.toResponse(course))
                .thenReturn(response);

        CourseResponseDTO result = courseService.create(request);

        assertEquals(response, result);
        verify(courseRepository).save(course);
        verifyNoInteractions(coordinatorRepository);
    }

    @Test
    void createAssociatesExistingCoordinator() {
        CourseRequestDTO request = new CourseRequestDTO(
                "Inglés",
                "Básico",
                "Curso inicial",
                10L
        );

        Course course = createCourse(1L, "Inglés", "Básico");
        Coordinator coordinator = new Coordinator();
        CourseResponseDTO response = createResponse(course);

        when(courseMapper.toEntity(request))
                .thenReturn(course);
        when(coordinatorRepository.findById(10L))
                .thenReturn(Optional.of(coordinator));
        when(courseRepository.save(course))
                .thenReturn(course);
        when(courseMapper.toResponse(course))
                .thenReturn(response);

        CourseResponseDTO result = courseService.create(request);

        assertEquals(response, result);
        assertEquals(coordinator, course.getCoordinator());
        verify(coordinatorRepository).findById(10L);
    }

    @Test
    void createThrowsExceptionWhenCoordinatorDoesNotExist() {
        CourseRequestDTO request = new CourseRequestDTO(
                "Inglés",
                "Básico",
                "Curso inicial",
                10L
        );

        Course course = createCourse(1L, "Inglés", "Básico");

        when(courseMapper.toEntity(request))
                .thenReturn(course);
        when(coordinatorRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.create(request)
        );
    }

    @Test
    void deleteThrowsExceptionWhenCourseDoesNotExist() {
        when(courseRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.delete(99L)
        );
    }

    @Test
    void deleteRemovesExistingCourse() {
        Course course = createCourse(1L, "Inglés", "Básico");

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        courseService.delete(1L);

        verify(courseRepository).delete(course);
    }

    private Course createCourse(Long id, String name, String level) {
        Course course = new Course();
        course.setId(id);
        course.setName(name);
        course.setLevel(level);
        return course;
    }

    private CourseResponseDTO createResponse(Course course) {
        return new CourseResponseDTO(
                course.getId(),
                course.getName(),
                course.getLevel(),
                course.getDescription(),
                null,
                null
        );
    }
}