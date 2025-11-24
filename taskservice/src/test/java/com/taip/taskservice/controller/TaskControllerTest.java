package com.taip.taskservice.controller;

import com.taip.taskservice.business.TaskService;
import com.taip.taskservice.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task("1", "TASK#1", "Test Task", "Test Description", false, "1");
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        when(taskService.createTask(any(Task.class))).thenReturn(testTask);

        Task result = taskController.createTask(testTask);

        assertNotNull(result);
        assertEquals(testTask.getId(), result.getId());
        verify(taskService).createTask(testTask);
    }

    @Test
    void getTask_ShouldReturnTask() {
        when(taskService.getTask("1")).thenReturn(testTask);

        Task result = taskController.getTask("1");

        assertNotNull(result);
        assertEquals(testTask.getId(), result.getId());
        verify(taskService).getTask("1");
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        List<Task> tasks = Arrays.asList(testTask);
        when(taskService.getAllTasks()).thenReturn(tasks);

        List<Task> result = taskController.getAllTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTask.getId(), result.get(0).getId());
        verify(taskService).getAllTasks();
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() {
        Task updatedTask = new Task("1", "TASK#1", "Updated Task", "Updated Description", true, "1");
        when(taskService.updateTask("1", testTask)).thenReturn(updatedTask);

        Task result = taskController.updateTask("1", testTask);

        assertNotNull(result);
        assertEquals(updatedTask.getName(), result.getName());
        verify(taskService).updateTask("1", testTask);
    }

    @Test
    void deleteTask_ShouldCallServiceDelete() {
        doNothing().when(taskService).deleteTask("1");

        assertDoesNotThrow(() -> taskController.deleteTask("1"));
        
        verify(taskService).deleteTask("1");
    }
}