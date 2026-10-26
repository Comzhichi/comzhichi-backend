package com.comzhichi.dto.response;

public record LoginResponseDTO(
        String accessToken,
        String tokenType,
        long expiresIn,
        String role
) {}