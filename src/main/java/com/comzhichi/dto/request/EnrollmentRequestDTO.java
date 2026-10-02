package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EnrollmentRequestDTO(
        @NotNull(message = "El ID del estudiante es obligatorio")
        Long studentId,

        @NotNull(message = "El ID de la sección es obligatorio")
        Long sectionId,

        @NotNull(message = "La fecha de matrícula es obligatoria")
        LocalDate enrollmentDate,

        @NotBlank(message = "El estado es obligatorio")
        @Size(max = 50, message = "El estado no debe exceder los 50 caracteres")
        String status
) {}