package com.taip.reportservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class ProjectClient {
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    private static final String PROJECTS_API = "http://localhost:8080";
    private static final String TASKS_API = "http://localhost:8081";
    
    public Map<String, Object> getProject(String projectId) {
        return restTemplate.getForObject(PROJECTS_API + "/projects/" + projectId, Map.class);
    }
    
    public List<Map<String, Object>> getTasksByProject(String projectId) {
        return restTemplate.getForObject(TASKS_API + "/tasks/project/" + projectId, List.class);
    }
}