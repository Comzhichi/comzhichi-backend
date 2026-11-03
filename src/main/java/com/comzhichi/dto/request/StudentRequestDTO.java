package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotNull;

public record StudentRequestDTO(
        @NotNull(message = "El ID de usuario es obligatorio")
        Long userId
) {}