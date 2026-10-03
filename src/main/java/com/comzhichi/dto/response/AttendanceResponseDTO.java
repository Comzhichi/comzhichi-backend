package com.comzhichi.dto.response;

import java.time.LocalDate;

public record AttendanceResponseDTO(
        Long id,
        Long enrollmentId,
        LocalDate date,
        String status,
        String observation
) {}