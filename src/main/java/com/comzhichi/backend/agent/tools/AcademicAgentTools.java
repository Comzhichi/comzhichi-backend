package com.comzhichi.backend.agent.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

// Stub inicial: solo searchSections está implementada.
// Las demás tools las completan los compañeros en sus propias ramas
// a partir de este archivo en develop.
@Component
@RequiredArgsConstructor
public class AcademicAgentTools {

    @Tool(description = """
            Busca secciones de cursos disponibles con vacantes.
            Filtra por idioma (ej. Inglés, Francés), nivel (ej. A1, B2) \
            o día de la semana.
            Úsala cuando el usuario pregunte qué cursos u horarios hay.
            """)
    public String searchSections(
            @ToolParam(description = "Idioma del curso, solo si el usuario lo menciona",
                    required = false) String language,
            @ToolParam(description = "Nivel del curso (A1, A2, B1…), solo si el usuario lo menciona",
                    required = false) String level,
            @ToolParam(description = "Día de la semana en inglés (MONDAY, TUESDAY…), solo si el usuario lo menciona",
                    required = false) String dayOfWeek) {
        // TODO: implementar llamando a SectionRepository o SectionService
        return "[]";
    }

    @Tool(description = """
            Devuelve el avance académico del estudiante autenticado por idioma.
            Úsala cuando el estudiante pregunte por su nivel, historial o progreso.
            """)
    public String getStudentProgress() {
        // TODO: US27 — implementar con EnrollmentService + historial por idioma
        return "{}";
    }

    @Tool(description = """
            Devuelve un resumen del desempeño de una sección: asistencia promedio, \
            notas promedio y observaciones destacadas.
            Úsala cuando un docente pida el resumen de un grupo.
            """)
    public String getGroupSummary(
            @ToolParam(description = "Código del grupo o nombre del curso") String groupCode) {
        // TODO: US28 — implementar con AttendanceService + EvaluationService
        return "{}";
    }

    @Tool(description = """
            Devuelve el avance, asistencia y calificaciones del estudiante vinculado \
            al familiar autenticado.
            Úsala cuando un familiar pregunte por su representado.
            """)
    public String getParentReport() {
        // TODO: US05 — implementar con StudentParentRepository + EnrollmentService
        return "{}";
    }

    @Tool(description = """
            Matricula al estudiante autenticado en la sección indicada.
            Con confirmRequested = false solo muestra la sección y pide confirmación \
            (CONFIRMATION_REQUIRED). Con confirmRequested = true registra la matrícula.
            """)
    public String enrollStudent(
            @ToolParam(description = "ID de la sección en la que el estudiante quiere matricularse")
            Long sectionId,
            @ToolParam(description = "true si el usuario ya confirmó la matrícula, false para pedir confirmación")
            boolean confirmRequested) {
        // TODO: US29 — implementar con EnrollmentService
        return "{\"status\": \"CONFIRMATION_REQUIRED\"}";
    }
}