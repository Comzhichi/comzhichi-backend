package com.comzhichi.dto.response;

public record TeacherResponseDTO(
        Long id,
        Long userId,
        String userEmail,
        String specialty
) {}