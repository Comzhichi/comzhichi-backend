package com.comzhichi.dto.response;

public record StudentParentResponseDTO(
        Long id,
        Long studentId,
        String studentEmail,
        Long parentId,
        String parentPhoneContact,
        String relationship,
        Boolean canEnroll
) {}