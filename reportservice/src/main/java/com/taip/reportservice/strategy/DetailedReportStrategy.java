package com.taip.reportservice.strategy;

import com.taip.reportservice.model.Report;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

public class DetailedReportStrategy implements ReportStrategy {
    @Override
    public Report generateReport(Map<String, Object> project, List<Map<String, Object>> tasks) {
        StringBuilder content = new StringBuilder("Detailed Report for Project: " + project.get("name") + "\n");
        for (Map<String, Object> task : tasks) {
            content.append("Task: ").append(task.get("name")).append("\n");
        }
        return new Report(null, "Detailed Report", content.toString(), LocalDate.now());
    }
}