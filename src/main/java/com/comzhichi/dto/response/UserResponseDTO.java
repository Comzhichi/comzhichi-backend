package com.comzhichi.dto.response;

import com.comzhichi.model.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        Role role,
        Boolean enabled
) {}