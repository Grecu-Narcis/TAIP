package com.taip.reportservice.repository;

import com.taip.reportservice.model.Report;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository {
    Report save(Report report);
    Report findById(String id);
    List<Report> findAll();
    void delete(String id);
}

