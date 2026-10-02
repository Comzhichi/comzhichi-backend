package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AttendanceRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,

        @NotBlank(message = "El estado es obligatorio")
        @Size(max = 50, message = "El estado no debe exceder los 50 caracteres")
        String status,

        String observation
) {}