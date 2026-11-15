package com.comzhichi.backend.agent.tools;

import com.comzhichi.backend.agent.dto.ParentReport;
import com.comzhichi.backend.agent.dto.ParentReportItem;
import com.comzhichi.config.CurrentUser;
import com.comzhichi.model.Attendance;
import com.comzhichi.model.AttendanceStatus;
import com.comzhichi.model.Enrollment;
import com.comzhichi.model.Evaluation;
import com.comzhichi.model.LevelEvaluation;
import com.comzhichi.model.LevelEvaluationResult;
import com.comzhichi.model.Parent;
import com.comzhichi.model.Role;
import com.comzhichi.model.Student;
import com.comzhichi.model.StudentParent;
import com.comzhichi.repository.AttendanceRepository;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.EvaluationRepository;
import com.comzhichi.repository.LevelEvaluationRepository;
import com.comzhichi.repository.ParentRepository;
import com.comzhichi.repository.StudentParentRepository;
import com.comzhichi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AcademicAgentTools {

    private final ParentRepository parentRepository;
    private final StudentParentRepository studentParentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final EvaluationRepository evaluationRepository;
    private final LevelEvaluationRepository levelEvaluationRepository;

    // --- US05: Reporte consolidado del familiar ---

    @Tool(description = """
            Devuelve un reporte consolidado del estudiante vinculado al familiar autenticado:
            nivel, promedio de notas, asistencias y porcentaje de inasistencias por curso/idioma.
            Úsala cuando un familiar pregunte por el avance, asistencia o notas de su representado.
            """)
    public ParentReport getParentReport() {
        UserPrincipal principal = CurrentUser.getPrincipal();
        if (principal == null || principal.getUser().getRole() != Role.PARENT) {
            return new ParentReport("NO_LINK",
                    "Tu cuenta no está vinculada como familiar. Requiere vincular un código de estudiante antes de consultar datos académicos.",
                    List.of());
        }

        Parent parent = parentRepository.findByUser_Id(principal.getUser().getId()).orElse(null);
        if (parent == null) {
            return new ParentReport("NO_LINK",
                    "No se encontró tu perfil de familiar. Requiere vincular un código de estudiante antes de consultar datos académicos.",
                    List.of());
        }

        List<StudentParent> links = studentParentRepository.findByParentId(parent.getId());
        if (links == null || links.isEmpty()) {
            return new ParentReport("NO_LINK",
                    "Tu cuenta no está vinculada a ningún estudiante. Requiere vincular un código de estudiante antes de consultar datos académicos.",
                    List.of());
        }

        List<ParentReportItem> items = new ArrayList<>();
        boolean hasRecords = false;

        for (StudentParent link : links) {
            Student student = link.getStudent();
            String studentName = student.getUser().getFullName();
            List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());
            if (enrollments == null || enrollments.isEmpty()) {
                continue;
            }
            for (Enrollment e : enrollments) {
                var course = e.getSection().getCourse();

                List<Attendance> attendances = attendanceRepository.findByEnrollmentId(e.getId());
                int totalAtt = attendances.size();
                int absences = (int) attendances.stream()
                        .filter(a -> a.getStatus() == AttendanceStatus.ABSENT)
                        .count();
                double absencePct = totalAtt == 0 ? 0.0 : (absences * 100.0 / totalAtt);

                List<Evaluation> evals = evaluationRepository.findByEnrollmentId(e.getId());
                Double avgScore = evals.isEmpty() ? null
                        : evals.stream().mapToInt(Evaluation::getScore).average().orElse(0.0);

                if (!attendances.isEmpty() || !evals.isEmpty()) {
                    hasRecords = true;
                }

                List<LevelEvaluation> levelEvals = levelEvaluationRepository.findByEnrollmentId(e.getId());
                String levelResult = null;
                if (!levelEvals.isEmpty()) {
                    boolean approved = levelEvals.stream()
                            .anyMatch(le -> le.getResult() == LevelEvaluationResult.APPROVED);
                    levelResult = approved ? "APPROVED"
                            : levelEvals.get(levelEvals.size() - 1).getResult().name();
                }

                items.add(new ParentReportItem(
                        studentName,
                        course.getLanguage(),
                        course.getName(),
                        course.getLevel(),
                        e.getStatus().name(),
                        e.getEnrollmentDate(),
                        levelResult,
                        totalAtt,
                        absences,
                        absencePct,
                        avgScore
                ));
            }
        }

        if (items.isEmpty() || !hasRecords) {
            return new ParentReport("NO_RECORDS",
                    "Aún no hay evaluaciones o registros publicados para el periodo activo de tu representado.",
                    items);
        }
        return new ParentReport("OK", "Reporte consolidado de tu familiar.", items);
    }

    // --- Stubs de otras tools (pendientes de otras US) ---

    @Tool(description = "Busca secciones de cursos disponibles con vacantes.")
    public String searchSections(
            @ToolParam(description = "Idioma del curso", required = false) String language,
            @ToolParam(description = "Nivel del curso (A1, A2, B1…)", required = false) String level,
            @ToolParam(description = "Día de la semana", required = false) String dayOfWeek) {
        return "[]";
    }

    @Tool(description = "Devuelve el avance académico del estudiante autenticado por idioma.")
    public String getStudentProgress() {
        return "{}";
    }

    @Tool(description = "Devuelve un resumen del desempeño de una sección.")
    public String getGroupSummary(
            @ToolParam(description = "Código del grupo o nombre del curso") String groupCode) {
        return "{}";
    }

    @Tool(description = "Matricula al estudiante autenticado en la sección indicada.")
    public String enrollStudent(
            @ToolParam(description = "ID de la sección") Long sectionId,
            @ToolParam(description = "true si el usuario ya confirmó") boolean confirmRequested) {
        return "{\"status\": \"CONFIRMATION_REQUIRED\"}";
    }
}