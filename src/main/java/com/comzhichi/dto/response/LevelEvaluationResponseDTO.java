package com.comzhichi.dto.response;

import java.time.LocalDate;

public record LevelEvaluationResponseDTO(
        Long id, Long studentId, Long courseId, LocalDate date, String result
) {}
