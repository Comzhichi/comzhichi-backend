package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequestDTO(
        @NotBlank(message = "El nombre del curso es obligatorio")
        @Size(max = 100, message = "El nombre no debe exceder los 100 caracteres")
        String name,

        @NotBlank(message = "El nivel es obligatorio")
        @Size(max = 50, message = "El nivel no debe exceder los 50 caracteres")
        String level,

        String description,

        Long coordinatorId
) {}