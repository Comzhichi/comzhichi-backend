package com.comzhichi.backend.agent.dto;

import java.time.LocalDate;

public record ParentReportItem(
        String studentName,
        String language,
        String courseName,
        String level,
        String enrollmentStatus,
        LocalDate enrollmentDate,
        String levelResult,
        int totalAttendances,
        int absences,
        double absencePercentage,
        Double averageScore
) {}