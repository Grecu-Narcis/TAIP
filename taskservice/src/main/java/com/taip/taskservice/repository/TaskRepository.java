package com.taip.taskservice.repository;

import com.taip.taskservice.model.Task;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository {
    Task save(Task task);
    Task findById(String id);
    List<Task> findAll();
    List<Task> findByProjectId(String projectId);
    void delete(String id);
}
