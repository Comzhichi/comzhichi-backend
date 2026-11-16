package com.comzhichi.dto.response;

// Lo que el agente ve de una sección disponible: solo lo necesario para responder
public record SectionSummary(
        Long id,
        String groupCode,
        String courseName,
        String courseLevel,
        String dayOfWeek,
        String startTime,
        String endTime,
        Integer availableVacancies
) {}
