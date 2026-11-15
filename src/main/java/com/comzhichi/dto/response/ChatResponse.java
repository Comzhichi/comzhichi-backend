package com.comzhichi.dto.response;

public record ChatResponse(
        String message,
        Object data,
        String action
) {}