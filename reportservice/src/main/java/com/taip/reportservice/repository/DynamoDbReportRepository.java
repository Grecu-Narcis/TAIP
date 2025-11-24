package com.taip.reportservice.repository;

import com.taip.reportservice.model.Report;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.LocalDate;
import java.util.*;

@Repository
public class DynamoDbReportRepository implements ReportRepository {
    
    private final DynamoDbClient dynamoDbClient;
    private static final String TABLE_NAME = "TAIP-Project";
    private static final String REPORT_ID_FORMAT = "REPORT#%s";
    
    public DynamoDbReportRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }
    
    @Override
    public Report save(Report report) {
        String reportId = UUID.randomUUID().toString();
        report.setId(reportId);
        
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("pk", AttributeValue.builder().s(String.format(REPORT_ID_FORMAT, reportId)).build());
        item.put("name", AttributeValue.builder().s(report.getName()).build());
        item.put("content", AttributeValue.builder().s(report.getContent()).build());
        item.put("generatedAt", AttributeValue.builder().s(report.getGeneratedAt().toString()).build());
        
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build());
        
        return report;
    }

    @Override
    public Report findById(String id) {
        GetItemResponse response = dynamoDbClient.getItem(GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(Map.of("pk", AttributeValue.builder().s(String.format(REPORT_ID_FORMAT, id)).build()))
                .build());
        
        if (!response.hasItem()) return null;
        
        Map<String, AttributeValue> item = response.item();
        return new Report(
                id,
                item.get("name").s(),
                item.get("content").s(),
                LocalDate.parse(item.get("generatedAt").s())
        );
    }

    @Override
    public List<Report> findAll() {
        ScanResponse response = dynamoDbClient.scan(ScanRequest.builder()
                .tableName(TABLE_NAME)
                .filterExpression("begins_with(pk, :prefix)")
                .expressionAttributeValues(Map.of(":prefix", AttributeValue.builder().s("REPORT#").build()))
                .build());
        
        return response.items().stream().map(item -> new Report(
                item.get("pk").s().substring(7),
                item.get("name").s(),
                item.get("content").s(),
                LocalDate.parse(item.get("generatedAt").s())
        )).toList();
    }

    @Override
    public void delete(String id) {
        dynamoDbClient.deleteItem(DeleteItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(Map.of("pk", AttributeValue.builder().s(String.format(REPORT_ID_FORMAT, id)).build()))
                .build());
    }
}