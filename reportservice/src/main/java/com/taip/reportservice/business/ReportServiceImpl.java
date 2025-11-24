package com.taip.reportservice.business;

import com.taip.reportservice.client.ProjectClient;
import com.taip.reportservice.factory.ReportStrategyFactory;
import com.taip.reportservice.model.Report;
import com.taip.reportservice.repository.DynamoDbReportRepository;
import com.taip.reportservice.strategy.ReportStrategy;
import com.taip.reportservice.strategy.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private DynamoDbReportRepository reportRepository;
    
    @Autowired
    private ProjectClient projectClient;

    @Override
    public Report generateReport(StrategyType type, String projectId) {
        Map<String, Object> project = projectClient.getProject(projectId);
        List<Map<String, Object>> tasks = projectClient.getTasksByProject(projectId);

        ReportStrategy strategy = ReportStrategyFactory.createStrategy(type);
        Report report = strategy.generateReport(project, tasks);
        return reportRepository.save(report);
    }
}

