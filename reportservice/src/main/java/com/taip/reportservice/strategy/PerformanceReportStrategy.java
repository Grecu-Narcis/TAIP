package com.taip.reportservice.strategy;

import com.taip.reportservice.model.Report;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;

public class PerformanceReportStrategy implements ReportStrategy {
    @Override
    public Report generateReport(Map<String, Object> project, List<Map<String, Object>> tasks) {
        long completedTasks = tasks.stream().mapToLong(task -> "COMPLETED".equals(task.get("status")) ? 1 : 0).sum();
        String content = "Performance Report for Project: " + project.get("name") + "\nCompleted Tasks: " + completedTasks + "/" + tasks.size();
        return new Report(null, "Performance Report", content, LocalDate.now());
    }
}