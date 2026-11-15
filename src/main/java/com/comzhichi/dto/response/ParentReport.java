package com.comzhichi.dto.response;

import java.util.List;

public record ParentReport(
        String status,
        String message,
        List<ParentReportItem> items
) {}