package com.taip.reportservice.business;

import com.taip.reportservice.model.Report;
import com.taip.reportservice.strategy.StrategyType;



public interface ReportService {
    Report generateReport(StrategyType type, String projectId);
}

