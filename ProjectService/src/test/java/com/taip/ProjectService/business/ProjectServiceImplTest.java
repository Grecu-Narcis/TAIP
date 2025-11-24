package com.taip.ProjectService.business;

import com.taip.ProjectService.model.Project;
import com.taip.ProjectService.repository.ProjectRepository;
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
class ProjectServiceImplTest {
    
    @Mock
    private ProjectRepository projectRepository;
    
    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void createProject_ShouldReturnCreatedProject() {
        Project inputProject = new Project(null, "Test Project", "Description", LocalDate.now());
        Project savedProject = new Project("1", "Test Project", "Description", LocalDate.now());
        when(projectRepository.save(inputProject)).thenReturn(savedProject);
        
        Project result = projectService.createProject(inputProject);
        
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Project", result.getName());
        verify(projectRepository).save(inputProject);
    }

    @Test
    void updateProject_ShouldReturnUpdatedProject() {
        Project inputProject = new Project("1", "Updated Project", "Updated Description", LocalDate.now());
        when(projectRepository.save(inputProject)).thenReturn(inputProject);
        
        Project result = projectService.updateProject("1", inputProject);
        
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Updated Project", result.getName());
        verify(projectRepository).save(inputProject);
    }

    @Test
    void getProject_ShouldReturnProject() {
        Project project = new Project("1", "Test Project", "Description", LocalDate.now());
        when(projectRepository.findById("1")).thenReturn(project);
        
        Project result = projectService.getProject("1");
        
        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(projectRepository).findById("1");
    }

    @Test
    void getAllProjects_ShouldReturnProjectList() {
        List<Project> projects = Arrays.asList(
            new Project("1", "Project 1", "Description 1", LocalDate.now()),
            new Project("2", "Project 2", "Description 2", LocalDate.now())
        );
        when(projectRepository.findAll()).thenReturn(projects);
        
        List<Project> result = projectService.getAllProjects();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(projectRepository).findAll();
    }

    @Test
    void deleteProject_ShouldCallRepository() {
        projectService.deleteProject("1");
        
        verify(projectRepository).delete("1");
    }
}