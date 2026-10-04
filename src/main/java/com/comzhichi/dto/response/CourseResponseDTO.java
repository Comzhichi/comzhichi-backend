package com.comzhichi.dto.response;

public record CourseResponseDTO(
        Long id,
        String name,
        String level,
        String description,
        Long coordinatorId,
        String coordinatorDepartment
) {}