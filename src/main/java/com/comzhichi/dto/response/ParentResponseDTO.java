package com.comzhichi.dto.response;

public record ParentResponseDTO(
        Long id,
        Long userId,
        String userFullName,
        String userEmail,
        String phoneContact
) {}