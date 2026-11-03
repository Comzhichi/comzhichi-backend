package com.comzhichi.dto.response;

import com.comzhichi.model.EvaluationType;
import java.time.LocalDate;

public record EvaluationResponseDTO(
        Long id,
        Long enrollmentId,
        String studentFullName,
        EvaluationType type,
        LocalDate date,
        Integer score,
        String feedback
) {}