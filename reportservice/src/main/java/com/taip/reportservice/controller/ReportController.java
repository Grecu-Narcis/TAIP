package com.taip.reportservice.controller;

import com.taip.reportservice.business.ReportService;
import com.taip.reportservice.model.Report;
import com.taip.reportservice.strategy.StrategyType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/summary/{projectId}")
    public Report generateSummaryReport(@PathVariable String projectId) {
        return reportService.generateReport(StrategyType.SUMMARY, projectId);
    }

    @PostMapping("/detailed/{projectId}")
    public Report generateDetailedReport(@PathVariable String projectId) {
        return reportService.generateReport(StrategyType.DETAILED, projectId);
    }

    @PostMapping("/performance/{projectId}")
    public Report generatePerformanceReport(@PathVariable String projectId) {
        return reportService.generateReport(StrategyType.PERFORMANCE, projectId);
    }
}