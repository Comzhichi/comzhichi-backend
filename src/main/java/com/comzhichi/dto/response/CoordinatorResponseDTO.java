package com.comzhichi.dto.response;

public record CoordinatorResponseDTO(
        Long id,
        Long userId,
        String userFullName,
        String userEmail,
        String department
) {}