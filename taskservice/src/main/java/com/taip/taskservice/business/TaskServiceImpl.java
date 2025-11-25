package com.taip.taskservice.business;

import com.taip.taskservice.config.Cacheable;
import com.taip.taskservice.config.CacheEvict;
import com.taip.taskservice.model.Task;
import com.taip.taskservice.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        return taskRepository.save(task);
    }

    @Cacheable
    @Override
    public Task getTask(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return taskRepository.findById(id);
    }

    @CacheEvict
    @Override
    public Task updateTask(String id, Task task) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        task.setId(id);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        taskRepository.delete(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByProjectId(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
