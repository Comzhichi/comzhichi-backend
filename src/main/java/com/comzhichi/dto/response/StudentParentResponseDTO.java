package com.comzhichi.dto.response;

public record StudentParentResponseDTO(
        Long id,
        Long studentId,
        String studentFullName,
        String studentEmail,
        Long parentId,
        String parentFullName,
        String parentEmail,
        String parentPhoneContact,
        String relationship,
        Boolean canEnroll
) {}