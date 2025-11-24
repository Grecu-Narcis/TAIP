package com.taip.reportservice.factory;

import com.taip.reportservice.strategy.*;

public class ReportStrategyFactory {
    public static ReportStrategy createStrategy(StrategyType type) {
        return switch (type) {
            case SUMMARY -> new SummaryReportStrategy();
            case DETAILED -> new DetailedReportStrategy();
            case PERFORMANCE -> new PerformanceReportStrategy();
        };
    }
}

