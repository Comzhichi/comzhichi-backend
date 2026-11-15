package com.comzhichi.backend.agent.dto;

import java.util.List;

public record ParentReport(
        String status,
        String message,
        List<ParentReportItem> items
) {}