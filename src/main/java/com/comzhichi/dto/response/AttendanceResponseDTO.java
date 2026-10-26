package com.comzhichi.dto.response;

import com.comzhichi.model.AttendanceStatus;
import java.time.LocalDate;

public record AttendanceResponseDTO(
        Long id,
        Long enrollmentId,
        String studentFullName,
        LocalDate date,
        AttendanceStatus status,
        String observation
) {}