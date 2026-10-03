package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record AttendanceRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,

        @NotBlank(message = "El estado es obligatorio")
        @Pattern(regexp = "(?i)PRESENT|ABSENT|LATE|JUSTIFIED", message = "El estado no es valido")
        String status,

        String observation
) {}