package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParentRequestDTO(
        @NotNull(message = "El ID de usuario es obligatorio")
        Long userId,

        @Size(max = 20, message = "El teléfono de contacto no debe exceder los 20 caracteres")
        String phoneContact
) {}