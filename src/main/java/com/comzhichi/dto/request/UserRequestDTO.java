package com.comzhichi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank @Size(min = 3, max = 50) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        @Size(max = 120) String fullName
) {}