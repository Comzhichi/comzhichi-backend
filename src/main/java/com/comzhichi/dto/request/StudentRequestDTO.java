package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentRequestDTO(
        @NotNull(message = "El ID de usuario es obligatorio")
        Long userId,

        @Size(max = 50, message = "El nivel no debe exceder los 50 caracteres")
        String level,

        @Size(max = 100, message = "La institución no debe exceder los 100 caracteres")
        String institution
) {}