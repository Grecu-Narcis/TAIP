package com.taip.reportservice.strategy;

import com.taip.reportservice.model.Report;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

public class SummaryReportStrategy implements ReportStrategy {
    @Override
    public Report generateReport(Map<String, Object> project, List<Map<String, Object>> tasks) {
        String content = "Summary Report for Project: " + project.get("name") + "\nTotal Tasks: " + tasks.size();
        return new Report(null, "Summary Report", content, LocalDate.now());
    }
}
