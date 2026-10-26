package com.comzhichi.dto.request;

import com.comzhichi.model.LevelEvaluationResult;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LevelEvaluationRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,

        @NotNull(message = "El resultado es obligatorio")
        LevelEvaluationResult result,

        String comments
) {}