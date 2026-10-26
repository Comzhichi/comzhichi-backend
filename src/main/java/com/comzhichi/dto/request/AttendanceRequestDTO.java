package com.comzhichi.dto.request;

import com.comzhichi.model.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AttendanceRequestDTO(
        @NotNull(message = "El ID de matrícula es obligatorio")
        Long enrollmentId,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,

        @NotNull(message = "El estado es obligatorio")
        AttendanceStatus status,

        String observation
) {}