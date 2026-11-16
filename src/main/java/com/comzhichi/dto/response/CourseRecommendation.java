package com.comzhichi.dto.response;

// Recomendación de siguiente curso para UN idioma (US27).
// El agente recibe una por cada idioma que estudia el alumno.
// status: OK (hay siguiente nivel), COMPLETED (ya aprobó el último disponible),
//         INSUFFICIENT_DATA (aún no hay historial aprobado en ese idioma).
public record CourseRecommendation(
        String status,
        String message,
        String language,
        String currentCourseName,
        String currentLevel,
        Long recommendedCourseId,
        String recommendedCourseName,
        String recommendedLevel
) {}
