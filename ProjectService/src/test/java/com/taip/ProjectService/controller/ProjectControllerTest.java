package com.taip.ProjectService.controller;

import com.taip.ProjectService.business.ProjectService;
import com.taip.ProjectService.model.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    void createProject_ShouldReturnCreatedProject() {
        Project inputProject = new Project(null, "Test Project", "Description", LocalDate.now());
        Project createdProject = new Project("1", "Test Project", "Description", LocalDate.now());
        when(projectService.createProject(inputProject)).thenReturn(createdProject);

        Project result = projectController.createProject(inputProject);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Project", result.getName());
        verify(projectService).createProject(inputProject);
    }

    @Test
    void getProject_ShouldReturnProject() {
        Project project = new Project("1", "Test Project", "Description", LocalDate.now());
        when(projectService.getProject("1")).thenReturn(project);

        Project result = projectController.getProject("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Project", result.getName());
        verify(projectService).getProject("1");
    }

    @Test
    void getAllProjects_ShouldReturnProjectList() {
        List<Project> projects = Arrays.asList(
            new Project("1", "Project 1", "Description 1", LocalDate.now()),
            new Project("2", "Project 2", "Description 2", LocalDate.now())
        );
        when(projectService.getAllProjects()).thenReturn(projects);

        List<Project> result = projectController.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectService).getAllProjects();
    }

    @Test
    void updateProject_ShouldReturnUpdatedProject() {
        Project inputProject = new Project("1", "Updated Project", "Updated Description", LocalDate.now());
        when(projectService.updateProject("1", inputProject)).thenReturn(inputProject);

        Project result = projectController.updateProject("1", inputProject);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Updated Project", result.getName());
        verify(projectService).updateProject("1", inputProject);
    }

    @Test
    void deleteProject_ShouldCallService() {
        projectController.deleteProject("1");

        verify(projectService).deleteProject("1");
    }
}