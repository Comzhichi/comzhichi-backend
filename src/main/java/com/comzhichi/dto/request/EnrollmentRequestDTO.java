package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record EnrollmentRequestDTO(
        @NotNull(message = "El ID del estudiante es obligatorio")
        Long studentId,

        @NotNull(message = "El ID de la sección es obligatorio")
        Long sectionId,

        @NotNull(message = "La fecha de matrícula es obligatoria")
        LocalDate enrollmentDate,

        @NotBlank(message = "El estado es obligatorio")
        @Pattern(regexp = "(?i)CONFIRMED|WITHDRAWN", message = "El estado no es valido")
        String status
) {}