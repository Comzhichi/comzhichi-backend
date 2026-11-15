package com.comzhichi.dto.response;

import java.util.List;

public record GroupSummary(
        String status,
        String message,
        Long sectionId,
        String groupCode,
        String courseName,
        String language,
        String level,
        int totalEnrollments,
        int activeEnrollments,
        double attendanceRate,
        Double averageScore,
        List<String> observations
) {}