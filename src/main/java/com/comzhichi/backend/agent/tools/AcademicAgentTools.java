package com.comzhichi.backend.agent.tools;

import com.comzhichi.dto.request.EnrollmentRequestDTO;
import com.comzhichi.dto.response.CourseRecommendation;
import com.comzhichi.dto.response.GroupSummary;
import com.comzhichi.dto.response.ParentReport;
import com.comzhichi.dto.response.ParentReportItem;
import com.comzhichi.dto.response.SectionSummary;
import com.comzhichi.config.CurrentUser;
import com.comzhichi.service.EnrollmentService;
import com.comzhichi.service.LevelRecommendationService;
import com.comzhichi.service.SectionService;
import org.springframework.security.access.AccessDeniedException;
import com.comzhichi.model.AcademicObservation;
import com.comzhichi.model.Attendance;
import com.comzhichi.model.AttendanceStatus;
import com.comzhichi.model.Enrollment;
import com.comzhichi.model.EnrollmentStatus;
import com.comzhichi.model.Evaluation;
import com.comzhichi.model.Section;
import com.comzhichi.model.LevelEvaluation;
import com.comzhichi.model.LevelEvaluationResult;
import com.comzhichi.model.Parent;
import com.comzhichi.model.Role;
import com.comzhichi.model.Student;
import com.comzhichi.model.StudentParent;
import com.comzhichi.repository.AcademicObservationRepository;
import com.comzhichi.repository.AttendanceRepository;
import com.comzhichi.repository.EnrollmentRepository;
import com.comzhichi.repository.SectionRepository;
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
    private final EnrollmentService enrollmentService;
    private final AttendanceRepository attendanceRepository;
    private final EvaluationRepository evaluationRepository;
    private final LevelEvaluationRepository levelEvaluationRepository;
    private final SectionRepository sectionRepository;
    private final AcademicObservationRepository academicObservationRepository;
    private final SectionService sectionService;
    private final LevelRecommendationService levelRecommendationService;

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

    // US26: el estudiante busca cursos; la regla (vacantes, cruce de horario) vive en SectionService
    @Tool(description = """
            Busca secciones de cursos con vacantes disponibles para el estudiante autenticado.
            Filtra por idioma, nivel o día de la semana, solo si el usuario los menciona.
            Excluye las secciones que se cruzan con sus matrículas activas.
            Úsala cuando el usuario pregunte qué cursos u horarios hay.
            """)
    public List<SectionSummary> searchSections(
            @ToolParam(description = "Idioma del curso, solo si el usuario lo menciona", required = false) String language,
            @ToolParam(description = "Nivel del curso (A1, A2, B1…), solo si el usuario lo menciona", required = false) String level,
            @ToolParam(description = "Día de la semana en inglés (MONDAY, TUESDAY…), solo si el usuario lo menciona", required = false) String dayOfWeek) {
        UserPrincipal principal = CurrentUser.getPrincipal();
        if (principal == null || principal.getUser().getRole() != Role.STUDENT) {
            throw new AccessDeniedException("Solo un estudiante puede buscar cursos para matricularse");
        }
        return sectionService.searchAvailableForUser(principal.getUser().getId(), language, level, dayOfWeek)
                .stream()
                .map(s -> new SectionSummary(s.id(), s.groupCode(), s.courseName(), s.courseLevel(),
                        s.dayOfWeek(), s.startTime(), s.endTime(), s.availableVacancies()))
                .limit(5)
                .toList();
    }

    @Tool(description = "Devuelve el avance académico del estudiante autenticado por idioma.")
    public String getStudentProgress() {
        return "{}";
    }

    // US27: recomienda el siguiente curso. La regla vive en LevelRecommendationService.
    @Tool(description = """
            Recomienda al estudiante autenticado qué curso o nivel llevar a continuación,
            analizando su historial académico. Devuelve una recomendación por cada idioma
            que estudia, de forma independiente.
            Úsala cuando pregunte qué curso le toca, qué sigue, a qué nivel puede pasar
            o qué le conviene llevar ahora.
            No la uses para listar la oferta de cursos: para eso está searchSections.
            """)
    public List<CourseRecommendation> recommendNextCourse() {
        UserPrincipal principal = CurrentUser.getPrincipal();
        if (principal == null || principal.getUser().getRole() != Role.STUDENT) {
            throw new AccessDeniedException("Solo un estudiante puede pedir una recomendación de curso");
        }
        return levelRecommendationService.recommendForUser(principal.getUser().getId());
    }

    @Tool(description = """
            Devuelve un resumen del desempeño de un grupo/sección: matrículas activas,
            tasa de asistencia, promedio de notas y observaciones registradas.
            Úsala cuando un docente pida un resumen del desempeño de un grupo.
            """)
    public GroupSummary getGroupSummary(
            @ToolParam(description = "Código del grupo (ej. ING-A1-A) o nombre del curso") String groupCode) {
        if (groupCode == null || groupCode.isBlank()) {
            return new GroupSummary("INSUFFICIENT_DATA",
                    "No se indicó el grupo. Indica el código del grupo (ej. ING-A1-A).",
                    null, null, null, null, null, 0, 0, 0.0, null, List.of());
        }

        Section section = sectionRepository.findAll().stream()
                .filter(s -> s.getGroupCode().equalsIgnoreCase(groupCode.trim()))
                .findFirst()
                .orElse(null);

        if (section == null) {
            return new GroupSummary("INSUFFICIENT_DATA",
                    "No se encontró el grupo " + groupCode + ".",
                    null, groupCode, null, null, null, 0, 0, 0.0, null, List.of());
        }

        List<Enrollment> enrollments = enrollmentRepository.findBySectionId(section.getId());
        if (enrollments == null || enrollments.isEmpty()) {
            return new GroupSummary("INSUFFICIENT_DATA",
                    "No hay estudiantes matriculados en el grupo " + section.getGroupCode() + ".",
                    section.getId(), section.getGroupCode(), section.getCourse().getName(),
                    section.getCourse().getLanguage(), section.getCourse().getLevel(),
                    0, 0, 0.0, null, List.of());
        }

        int total = enrollments.size();
        int active = (int) enrollments.stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .count();

        int attTotal = 0;
        int present = 0;
        double scoreSum = 0.0;
        int scoreCount = 0;
        List<String> observations = new ArrayList<>();

        for (Enrollment e : enrollments) {
            for (Attendance a : attendanceRepository.findByEnrollmentId(e.getId())) {
                attTotal++;
                if (a.getStatus() == AttendanceStatus.PRESENT) {
                    present++;
                }
            }
            for (Evaluation ev : evaluationRepository.findByEnrollmentId(e.getId())) {
                scoreSum += ev.getScore();
                scoreCount++;
            }
            for (AcademicObservation o : academicObservationRepository.findByEnrollmentId(e.getId())) {
                observations.add(o.getObservation());
            }
        }

        double attendanceRate = attTotal == 0 ? 0.0 : (present * 100.0 / attTotal);
        Double avgScore = scoreCount == 0 ? null : scoreSum / scoreCount;

        boolean hasData = attTotal > 0 || scoreCount > 0 || !observations.isEmpty();
        if (!hasData) {
            return new GroupSummary("INSUFFICIENT_DATA",
                    "No existen datos suficientes para generar el resumen del grupo " + section.getGroupCode() + ".",
                    section.getId(), section.getGroupCode(), section.getCourse().getName(),
                    section.getCourse().getLanguage(), section.getCourse().getLevel(),
                    total, active, attendanceRate, avgScore, observations);
        }

        return new GroupSummary("OK",
                "Resumen del desempeño del grupo " + section.getGroupCode() + ".",
                section.getId(), section.getGroupCode(), section.getCourse().getName(),
                section.getCourse().getLanguage(), section.getCourse().getLevel(),
                total, active, attendanceRate, avgScore, observations);
    }

    // US29: Matricula al estudiante autenticado en la sección indicada.
    @Tool(description = """
        Matricula al estudiante autenticado en la sección indicada.
        Con confirmRequested = false muestra el resumen de la sección y pide confirmación
        (devuelve CONFIRMATION_REQUIRED). Con confirmRequested = true registra la matrícula.
        Úsala cuando el estudiante pida matricularse en un curso específico.
        """)
    public String enrollStudent(
            @ToolParam(description = "ID de la sección en la que el estudiante quiere matricularse")
            Long sectionId,
            @ToolParam(description = "true si el usuario ya confirmó la matrícula, false para pedir confirmación")
            boolean confirmRequested) {

        UserPrincipal principal = CurrentUser.getPrincipal();
        if (principal == null || principal.getUser().getRole() != Role.STUDENT) {
            throw new AccessDeniedException("Solo un estudiante puede matricularse");
        }

        Section section = sectionRepository.findById(sectionId).orElse(null);
        if (section == null) {
            return "{\"status\": \"NOT_FOUND\", \"message\": \"No se encontró la sección indicada.\"}";
        }

        if (section.getAvailableVacancies() <= 0) {
            return "{\"status\": \"NO_VACANCIES\", \"message\": \"La sección no tiene vacantes disponibles.\"}";
        }

        if (!confirmRequested) {
            return String.format(
                    "{\"status\": \"CONFIRMATION_REQUIRED\", \"sectionId\": %d, \"groupCode\": \"%s\", \"course\": \"%s\", \"level\": \"%s\", \"schedule\": \"%s %s-%s\", \"vacancies\": %d}",
                    section.getId(),
                    section.getGroupCode(),
                    section.getCourse().getName(),
                    section.getCourse().getLevel(),
                    section.getDayOfWeek(),
                    section.getStartTime(),
                    section.getEndTime(),
                    section.getAvailableVacancies()
            );
        }

        try {
            EnrollmentRequestDTO dto = new EnrollmentRequestDTO(null, sectionId, java.time.LocalDate.now(), EnrollmentStatus.ACTIVE);
            enrollmentService.create(dto);
            return String.format(
                    "{\"status\": \"ENROLLED\", \"message\": \"Te matriculaste correctamente en %s (%s).\", \"groupCode\": \"%s\"}",
                    section.getCourse().getName(),
                    section.getCourse().getLevel(),
                    section.getGroupCode()
            );
        } catch (com.comzhichi.exception.BusinessRuleException ex) {
            return String.format(
                    "{\"status\": \"REJECTED\", \"message\": \"%s\"}",
                    ex.getMessage()
            );
        }
    }
}