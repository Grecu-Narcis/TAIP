package com.taip.ProjectService.business;

import com.taip.ProjectService.model.Project;
import com.taip.ProjectService.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(String id, Project project) {
        project.setId(id);
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.delete(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProject(String id) {
        return projectRepository.findById(id);
    }
}
