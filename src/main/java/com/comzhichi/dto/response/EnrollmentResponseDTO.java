package com.comzhichi.dto.response;

import java.time.LocalDate;

public record EnrollmentResponseDTO(
                Long id,
                Long studentId,
                String studentEmail,
                Long sectionId,
                String sectionGroupCode,
                String courseName,
                LocalDate enrollmentDate,
                String status) {
}