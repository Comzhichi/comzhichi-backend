package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TeacherRequestDTO(
        @NotNull(message = "El ID de usuario es obligatorio")
        Long userId,

        @Size(max = 100, message = "La especialidad no debe exceder los 100 caracteres")
        String specialty
) {}