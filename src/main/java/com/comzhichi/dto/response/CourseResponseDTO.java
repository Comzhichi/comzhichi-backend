package com.comzhichi.dto.response;

public record CourseResponseDTO(
        Long id,
        String name,
        String language,
        String level,
        String description,
        Long coordinatorId,
        String coordinatorDepartment,
        Long prerequisiteId,
        String prerequisiteName
) {}