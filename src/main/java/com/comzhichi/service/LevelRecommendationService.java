package com.comzhichi.service;

import com.comzhichi.dto.response.CourseRecommendation;
import com.comzhichi.exception.ResourceNotFoundException;
import com.comzhichi.model.Course;
import com.comzhichi.model.Enrollment;
import com.comzhichi.model.EnrollmentStatus;
import com.comzhichi.model.LevelEvaluationResult;
import com.comzhichi.model.Student;
import com.comzhichi.repository.CourseRepository;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.LevelEvaluationRepository;
import com.comzhichi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * US27: recomienda el siguiente curso a partir del historial académico.
 *
 * La regla se apoya en el prerequisito que ya define el modelo (Course.prerequisite):
 * el siguiente curso es aquel cuyo prerequisito es el último curso aprobado.
 * No se deduce del nombre del nivel, para no asumir una nomenclatura fija.
 *
 * Cada idioma se evalúa por separado, como pide el criterio alternativo de la US.
 */
@Service
@RequiredArgsConstructor
public class LevelRecommendationService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final LevelEvaluationRepository levelEvaluationRepository;

    @Transactional(readOnly = true)
    public List<CourseRecommendation> recommendForUser(Long userId) {
        Student student = studentRepository.findByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estudiante no encontrado para el usuario " + userId));

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());
        if (enrollments == null || enrollments.isEmpty()) {
            return List.of(new CourseRecommendation("INSUFFICIENT_DATA",
                    "El estudiante no tiene historial académico registrado.",
                    null, null, null, null, null, null));
        }

        // Un grupo por idioma, conservando el orden en que aparecen.
        Map<String, List<Enrollment>> byLanguage = new LinkedHashMap<>();
        for (Enrollment e : enrollments) {
            String language = e.getSection().getCourse().getLanguage();
            byLanguage.computeIfAbsent(language, k -> new ArrayList<>()).add(e);
        }

        List<CourseRecommendation> result = new ArrayList<>();
        for (Map.Entry<String, List<Enrollment>> entry : byLanguage.entrySet()) {
            result.add(recommendForLanguage(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    private CourseRecommendation recommendForLanguage(String language, List<Enrollment> enrollments) {
        List<Course> approved = enrollments.stream()
                .filter(this::isApproved)
                .map(e -> e.getSection().getCourse())
                .toList();

        if (approved.isEmpty()) {
            boolean studying = enrollments.stream()
                    .anyMatch(e -> EnrollmentStatus.ACTIVE.equals(e.getStatus()));
            String message = studying
                    ? "Aún está cursando " + language + " y no hay un nivel aprobado, "
                      + "así que todavía no se puede determinar el siguiente curso."
                    : "No hay cursos aprobados en " + language + ", "
                      + "así que no se puede determinar el siguiente curso.";
            return new CourseRecommendation("INSUFFICIENT_DATA", message,
                    language, null, null, null, null, null);
        }

        // El nivel actual es el curso aprobado más avanzado: el que no es
        // prerequisito de ningún otro curso aprobado.
        Course current = approved.stream()
                .filter(c -> approved.stream().noneMatch(
                        other -> sameCourse(other.getPrerequisite(), c)))
                .findFirst()
                .orElse(approved.get(approved.size() - 1));

        // Cursos ya aprobados o en curso: no se recomiendan de nuevo.
        List<Long> taken = enrollments.stream()
                .filter(e -> isApproved(e) || EnrollmentStatus.ACTIVE.equals(e.getStatus()))
                .map(e -> e.getSection().getCourse().getId())
                .toList();

        Course next = courseRepository.findAll().stream()
                .filter(c -> sameCourse(c.getPrerequisite(), current))
                .filter(c -> !taken.contains(c.getId()))
                .findFirst()
                .orElse(null);

        if (next == null) {
            return new CourseRecommendation("COMPLETED",
                    "Ya aprobó " + current.getName()
                            + " y no hay un curso posterior registrado en " + language + ".",
                    language, current.getName(), current.getLevel(), null, null, null);
        }

        return new CourseRecommendation("OK",
                "Siguiente curso según el historial de " + language + ".",
                language, current.getName(), current.getLevel(),
                next.getId(), next.getName(), next.getLevel());
    }

    /** Aprobado por estado de matrícula o por evaluación de nivel aprobada. */
    private boolean isApproved(Enrollment enrollment) {
        if (EnrollmentStatus.APPROVED.equals(enrollment.getStatus())) {
            return true;
        }
        if (enrollment.getId() == null) {
            return false;
        }
        return levelEvaluationRepository.findByEnrollmentId(enrollment.getId()).stream()
                .anyMatch(ev -> ev.getResult() == LevelEvaluationResult.APPROVED);
    }

    private boolean sameCourse(Course a, Course b) {
        return a != null && b != null && a.getId() != null && a.getId().equals(b.getId());
    }
}
