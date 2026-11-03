package com.comzhichi.dto.response;

public record SectionResponseDTO(
        Long id,
        String groupCode,
        String dayOfWeek,
        String startTime,
        String endTime,
        String classroom,
        Integer totalVacancies,
        Integer availableVacancies,
        Long courseId,
        String courseName,
        String courseLevel,
        String courseDescription,
        Long teacherId,
        String teacherSpecialty,
        String teacherName
) {}