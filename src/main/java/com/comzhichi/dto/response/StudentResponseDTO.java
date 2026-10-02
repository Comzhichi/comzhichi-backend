package com.comzhichi.dto.response;

public record StudentResponseDTO(
        Long id,
        Long userId,
        String userEmail,
        String level,
        String institution
) {}