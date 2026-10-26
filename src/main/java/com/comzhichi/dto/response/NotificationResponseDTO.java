package com.comzhichi.dto.response;

import java.time.LocalDateTime;

public record NotificationResponseDTO(
        Long id,
        String message,
        boolean read,
        LocalDateTime createdAt
) {}