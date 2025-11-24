package com.taip.taskservice.repository;

import com.taip.taskservice.model.Task;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class DynamoDbTaskRepository implements TaskRepository {
    private static final String PROJECT_ID_TO_TASKS = "ProjectIdToTasks";
    private final DynamoDbClient ddbClient;
    private final static String TABLE_NAME = "TAIP-Project";
    private final static String TASK_ID_FORMAT = "TASK#%s";

    public DynamoDbTaskRepository(DynamoDbClient client) {
        this.ddbClient = client;
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID().toString());
        }

        task.setPk(String.format(TASK_ID_FORMAT, task.getId()));
        
        Map<String, AttributeValue> item = Map.of(
            "pk", AttributeValue.builder().s(task.getPk()).build(),
            "id", AttributeValue.builder().s(task.getId()).build(),
            "name", AttributeValue.builder().s(task.getName()).build(),
            "description", AttributeValue.builder().s(task.getDescription()).build(),
            "completed", AttributeValue.builder().bool(task.isCompleted()).build(),
            "projectId", AttributeValue.builder().s(task.getProjectId()).build()
        );

        ddbClient.putItem(builder -> builder.tableName(TABLE_NAME).item(item));
        return task;
    }

    @Override
    public Task findById(String id) {
        String pk = String.format(TASK_ID_FORMAT, id);
        var response = ddbClient.getItem(builder -> 
            builder.tableName(TABLE_NAME).key(Map.of("pk", AttributeValue.builder().s(pk).build()))
        );
        
        if (!response.hasItem()) return null;
        
        Map<String, AttributeValue> item = response.item();
        Task task = new Task();
        task.setId(id);
        task.setPk(item.get("pk").s());
        task.setName(item.get("name").s());
        task.setDescription(item.get("description").s());
        task.setCompleted(item.get("completed").bool());
        task.setProjectId(item.get("projectId").s());
        return task;
    }

    @Override
    public List<Task> findAll() {
        var response = ddbClient.scan(builder -> 
            builder.tableName(TABLE_NAME)
                .filterExpression("begins_with(pk, :prefix)")
                .expressionAttributeValues(Map.of(":prefix", AttributeValue.builder().s("TASK#").build()))
        );
        
        return response.items().stream().map(item -> {
            Task task = new Task();
            task.setId(item.get("pk").s().substring(5));
            task.setPk(item.get("pk").s());
            task.setName(item.get("name").s());
            task.setDescription(item.get("description").s());
            task.setCompleted(item.get("completed").bool());
            task.setProjectId(item.get("projectId").s());
            return task;
        }).toList();
    }

    @Override
    public List<Task> findByProjectId(String projectId) {
        var response = ddbClient.query(builder -> 
            builder.tableName(TABLE_NAME)
                .indexName(PROJECT_ID_TO_TASKS)
                .keyConditionExpression("projectId = :projectId")
                .expressionAttributeValues(Map.of(":projectId", AttributeValue.builder().s(projectId).build()))
        );
        
        return response.items().stream().map(item -> {
            Task task = new Task();
            task.setId(item.get("pk").s().substring(5));
            task.setPk(item.get("pk").s());
            task.setName(item.get("name").s());
            task.setDescription(item.get("description").s());
            task.setCompleted(item.get("completed").bool());
            task.setProjectId(item.get("projectId").s());
            return task;
        }).toList();
    }

    @Override
    public void delete(String id) {
        String pk = String.format(TASK_ID_FORMAT, id);
        ddbClient.deleteItem(builder -> 
            builder.tableName(TABLE_NAME).key(Map.of("pk", AttributeValue.builder().s(pk).build()))
        );
    }
}