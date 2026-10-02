package com.comzhichi.dto.response;

public record AuthResponseDTO(
        String token,
        String tokenType,
        UserResponseDTO user
) {}