package com.comzhichi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SectionRequestDTO(
        @NotBlank(message = "El nombre de la sección es obligatorio")
        @Size(max = 100, message = "El nombre de la sección no debe exceder los 100 caracteres")
        String name,

        @NotBlank(message = "El código de grupo es obligatorio")
        @Size(max = 20, message = "El código de grupo no debe exceder los 20 caracteres")
        String groupCode,

        @NotBlank(message = "El horario es obligatorio")
        @Size(max = 100, message = "El horario no debe exceder los 100 caracteres")
        String schedule,

        @Size(max = 50, message = "El aula no debe exceder los 50 caracteres")
        String classroom,

        @NotNull(message = "El total de vacantes es obligatorio")
        @Min(value = 1, message = "Debe haber al menos 1 vacante")
        Integer totalVacancies,

        @NotNull(message = "Las vacantes disponibles son obligatorias")
        @Min(value = 0, message = "Las vacantes disponibles no pueden ser negativas")
        Integer availableVacancies,

        @NotNull(message = "El ID del curso es obligatorio")
        Long courseId,

        Long teacherId
) {}
