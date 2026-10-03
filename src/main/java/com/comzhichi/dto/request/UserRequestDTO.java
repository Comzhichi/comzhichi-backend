package com.comzhichi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.comzhichi.model.Role;

public record UserRequestDTO(
        @NotBlank @Size(min = 3, max = 50) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        @NotNull Role role
) {}
