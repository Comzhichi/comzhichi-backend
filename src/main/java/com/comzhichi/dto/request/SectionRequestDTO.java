package com.comzhichi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SectionRequestDTO(
        @NotBlank(message = "El código de grupo es obligatorio")
        @Size(max = 20)
        String groupCode,

        @NotBlank(message = "El día de semana es obligatorio")
        @Size(max = 50)
        String dayOfWeek,   // ej. "MONDAY,WEDNESDAY"

        @NotBlank(message = "La hora de inicio es obligatoria")
        @Size(max = 10)
        String startTime,   // ej. "08:00"

        @NotBlank(message = "La hora de fin es obligatoria")
        @Size(max = 10)
        String endTime,     // ej. "10:00"

        @Size(max = 50)
        String classroom,

        @NotNull
        @Min(value = 1)
        Integer totalVacancies,

        @NotNull
        @Min(value = 0)
        Integer availableVacancies,

        @NotNull(message = "El ID del curso es obligatorio")
        Long courseId,

        Long teacherId
) {}