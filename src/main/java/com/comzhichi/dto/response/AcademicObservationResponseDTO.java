package com.comzhichi.dto.response;

import java.time.LocalDate;

public record AcademicObservationResponseDTO(
        Long id,
        Long enrollmentId,
        LocalDate date,
        String observation
) {}
