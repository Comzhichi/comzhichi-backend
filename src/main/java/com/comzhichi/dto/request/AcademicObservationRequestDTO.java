package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AcademicObservationRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,
        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,
        @NotBlank(message = "La observación es obligatoria")
        String observation
) {}
