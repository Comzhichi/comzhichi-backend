package com.comzhichi.dto.request;

import com.comzhichi.model.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EnrollmentRequestDTO(
        @NotNull(message = "El ID del estudiante es obligatorio")
        Long studentId,

        @NotNull(message = "El ID de la sección es obligatorio")
        Long sectionId,

        @NotNull(message = "La fecha de matrícula es obligatoria")
        LocalDate enrollmentDate,

        @NotNull(message = "El estado es obligatorio")
        EnrollmentStatus status
) {}