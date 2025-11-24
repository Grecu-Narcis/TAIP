package com.taip.taskservice.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Task {
    private String id;
    private String pk;
    private String name;
    private String description;
    private boolean completed;
    private String projectId;

    @DynamoDbPartitionKey
    public String getPk() {
        return pk;
    }
}

// mock comment
