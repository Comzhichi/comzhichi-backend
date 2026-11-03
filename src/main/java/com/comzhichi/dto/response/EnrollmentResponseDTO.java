package com.comzhichi.dto.response;

import com.comzhichi.model.EnrollmentStatus;
import java.time.LocalDate;

public record EnrollmentResponseDTO(
        Long id,
        Long studentId,
        String studentFullName,
        String studentEmail,
        Long sectionId,
        String sectionGroupCode,
        String courseName,
        String sectionDayOfWeek,
        String sectionStartTime,
        String sectionEndTime,
        String sectionClassroom,
        LocalDate enrollmentDate,
        EnrollmentStatus status
) {}