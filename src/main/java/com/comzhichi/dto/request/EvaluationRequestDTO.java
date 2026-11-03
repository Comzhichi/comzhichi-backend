package com.comzhichi.dto.request;

import com.comzhichi.model.EvaluationType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EvaluationRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,

        @NotNull(message = "El tipo de evaluación es obligatorio")
        EvaluationType type,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,

        @NotNull(message = "La nota es obligatoria")
        @Min(value = 0) @Max(value = 100)
        Integer score,

        String feedback
) {}