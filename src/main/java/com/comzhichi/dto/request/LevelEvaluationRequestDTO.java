package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record LevelEvaluationRequestDTO(
        @NotNull Long studentId,
        @NotNull Long courseId,
        @NotNull LocalDate date,
        @NotBlank @Pattern(regexp = "APPROVED|REPEAT", message = "El resultado debe ser APPROVED o REPEAT")
        String result
) {}
