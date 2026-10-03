package com.comzhichi.dto.response;

public record EvaluationResponseDTO(
        Long id,
        Long enrollmentId,
        String name,
        String description,
        Integer score,
        String feedback
) {}