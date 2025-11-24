package com.taip.ProjectService.repository;

import com.taip.ProjectService.model.Project;
import java.util.List;

public interface ProjectRepository {
    Project save(Project project);
    Project findById(String id);
    List<Project> findAll();
    void delete(String id);
}

