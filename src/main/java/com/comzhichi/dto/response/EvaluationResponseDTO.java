package com.comzhichi.dto.response;

public record EvaluationResponseDTO(
        Long id,
        Long enrollmentId,
        Integer score,
        String feedback
) {}