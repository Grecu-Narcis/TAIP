package com.taip.ProjectService.business;

import com.taip.ProjectService.model.Project;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project);
    Project updateProject(String id, Project project);
    void deleteProject(String id);
    List<Project> getAllProjects();
    Project getProject(String id);
}
