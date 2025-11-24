package com.taip.taskservice.business;

import com.taip.taskservice.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task getTask(String id);
    Task updateTask(String id, Task task);
    void deleteTask(String id);
    List<Task> getAllTasks();
    List<Task> getTasksByProjectId(String projectId);
}
