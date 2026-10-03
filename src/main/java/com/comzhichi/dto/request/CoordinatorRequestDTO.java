package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CoordinatorRequestDTO(
        @NotNull(message = "El ID de usuario es obligatorio")
        Long userId,

        @Size(max = 100, message = "El departamento no debe exceder los 100 caracteres")
        String department
) {}