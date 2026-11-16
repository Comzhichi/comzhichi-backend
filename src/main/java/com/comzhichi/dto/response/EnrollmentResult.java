package com.comzhichi.dto.response;

// Lo que el agente ve al pedir una matrícula.
// status: CONFIRMATION_REQUIRED (falta que el usuario confirme),
//         OK (matriculado), REJECTED (no se pudo), INSUFFICIENT_DATA (falta la sección).
public record EnrollmentResult(
        String status,
        String message,
        Long sectionId,
        String groupCode,
        String courseName,
        String courseLevel,
        String dayOfWeek,
        String startTime,
        String endTime,
        Integer availableVacancies,
        Long enrollmentId
) {}
