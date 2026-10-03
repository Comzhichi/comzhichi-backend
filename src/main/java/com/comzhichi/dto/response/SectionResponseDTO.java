package com.comzhichi.dto.response;

public record SectionResponseDTO(
                Long id,
                String name,
                String groupCode,
                String schedule,
                String classroom,
                Integer totalVacancies,
                Integer availableVacancies,
                Long courseId,
                String courseName,
                Long teacherId,
                String teacherSpecialty) {
}
