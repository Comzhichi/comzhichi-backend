package com.comzhichi.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EvaluationRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,

        @NotBlank(message = "El nombre de la evaluacion es obligatorio")
        @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
        String name,

        String description,

        @Min(value = 0, message = "La nota mínima es 0")
        @Max(value = 100, message = "La nota máxima es 100")
        Integer score,

        String feedback
) {}