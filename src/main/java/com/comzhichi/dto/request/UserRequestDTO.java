package com.comzhichi.dto.request;

import com.comzhichi.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        @NotNull Role role,
        @Size(max = 100) String specialty,    // solo TEACHER
        @Size(max = 100) String department,   // solo COORDINATOR
        @Size(max = 20)  String phoneContact,  // solo PARENT
        @Size(max = 120) String studentEmail   // solo PARENT (vinculación)
) {}