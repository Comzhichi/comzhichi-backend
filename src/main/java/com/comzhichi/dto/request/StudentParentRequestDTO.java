package com.comzhichi.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentParentRequestDTO(
        @NotNull(message = "El ID del estudiante es obligatorio")
        Long studentId,

        @NotNull(message = "El ID del apoderado es obligatorio")
        Long parentId,

        @Size(max = 50, message = "El parentesco no debe exceder los 50 caracteres")
        String relationship,

        Boolean canEnroll
) {}