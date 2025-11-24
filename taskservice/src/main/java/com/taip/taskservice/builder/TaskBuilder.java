package com.taip.taskservice.builder;

import com.taip.taskservice.model.Task;

public class TaskBuilder {
    private String id;
    private String pk;
    private String name;
    private String description;
    private boolean completed;
    private String projectId;

    public TaskBuilder setId(String id) { this.id = id; return this; }
    public TaskBuilder setPk(String pk) { this.pk = pk; return this; }
    public TaskBuilder setName(String name) { this.name = name; return this; }
    public TaskBuilder setDescription(String description) { this.description = description; return this; }
    public TaskBuilder setCompleted(boolean completed) { this.completed = completed; return this; }
    public TaskBuilder setProjectId(String projectId) { this.projectId = projectId; return this; }

    public Task build() { return new Task(id, pk, name, description, completed, projectId); }
}

