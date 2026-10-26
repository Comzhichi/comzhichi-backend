package com.comzhichi.dto.response;

import com.comzhichi.model.LevelEvaluationResult;
import java.time.LocalDate;

public record LevelEvaluationResponseDTO(
        Long id,
        Long enrollmentId,
        String studentFullName,
        String courseName,
        String language,
        LocalDate date,
        LevelEvaluationResult result,
        String comments
) {}