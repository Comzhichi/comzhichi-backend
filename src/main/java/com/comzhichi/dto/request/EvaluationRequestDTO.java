package com.comzhichi.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EvaluationRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,

        @Min(value = 0, message = "La nota mínima es 0")
        @Max(value = 100, message = "La nota máxima es 100")
        Integer score,

        String feedback
) {}