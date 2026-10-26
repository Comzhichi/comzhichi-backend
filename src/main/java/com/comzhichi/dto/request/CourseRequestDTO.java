package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequestDTO(
        @NotBlank(message = "El nombre del curso es obligatorio")
        @Size(max = 100)
        String name,

        @NotBlank(message = "El idioma es obligatorio")
        @Size(max = 50)
        String language,

        @NotBlank(message = "El nivel es obligatorio")
        @Size(max = 50)
        String level,

        String description,

        Long coordinatorId,
        Long prerequisiteId   // nullable
) {}