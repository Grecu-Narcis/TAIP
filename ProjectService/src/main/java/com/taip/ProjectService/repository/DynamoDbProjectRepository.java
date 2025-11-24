package com.taip.ProjectService.repository;

import com.taip.ProjectService.builder.ProjectBuilder;
import com.taip.ProjectService.model.Project;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.time.LocalDate;
import java.util.*;

@Repository
public class DynamoDbProjectRepository implements ProjectRepository {
    private final DynamoDbClient ddbClient;
    private static final String TABLE_NAME = "TAIP-Project";
    private static final String PROJECT_ID_FORMAT = "PROJECT#%s";

    public DynamoDbProjectRepository(DynamoDbClient client) {
        this.ddbClient = client;
    }

    @Override
    public Project save(Project project) {
        if (project.getId() == null) {
            project.setId(UUID.randomUUID().toString());
        }
        
        String pk = String.format(PROJECT_ID_FORMAT, project.getId());
        
        Map<String, AttributeValue> item = Map.of(
            "pk", AttributeValue.builder().s(pk).build(),
            "id", AttributeValue.builder().s(project.getId()).build(),
            "name", AttributeValue.builder().s(project.getName()).build(),
            "description", AttributeValue.builder().s(project.getDescription()).build(),
            "deadline", AttributeValue.builder().s(project.getDeadline().toString()).build()
        );

        ddbClient.putItem(builder -> builder.tableName(TABLE_NAME).item(item));
        return project;
    }

    @Override
    public Project findById(String id) {
        String pk = String.format(PROJECT_ID_FORMAT, id);
        var response = ddbClient.getItem(builder -> 
            builder.tableName(TABLE_NAME).key(Map.of("pk", AttributeValue.builder().s(pk).build()))
        );
        
        if (!response.hasItem()) return null;
        
        Map<String, AttributeValue> item = response.item();
        return new ProjectBuilder()
            .setId(item.get("id").s())
            .setName(item.get("name").s())
            .setDescription(item.get("description").s())
            .setDeadline(LocalDate.parse(item.get("deadline").s()))
            .build();
    }

    @Override
    public List<Project> findAll() {
        var response = ddbClient.scan(builder -> 
            builder.tableName(TABLE_NAME)
                .filterExpression("begins_with(pk, :prefix)")
                .expressionAttributeValues(Map.of(":prefix", AttributeValue.builder().s("PROJECT#").build()))
        );
        
        return response.items().stream().map(item -> new ProjectBuilder()
            .setId(item.get("id").s())
            .setName(item.get("name").s())
            .setDescription(item.get("description").s())
            .setDeadline(LocalDate.parse(item.get("deadline").s()))
            .build()
        ).toList();
    }

    @Override
    public void delete(String id) {
        String pk = String.format(PROJECT_ID_FORMAT, id);
        ddbClient.deleteItem(builder -> 
            builder.tableName(TABLE_NAME).key(Map.of("pk", AttributeValue.builder().s(pk).build()))
        );
    }
}