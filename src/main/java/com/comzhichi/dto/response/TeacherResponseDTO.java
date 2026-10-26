package com.comzhichi.dto.response;

public record TeacherResponseDTO(
        Long id,
        Long userId,
        String userFullName,
        String userEmail,
        String specialty
) {}