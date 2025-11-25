package com.taip.reportservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class ProjectClient {
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    private static final String PROJECTS_API = "http://a5433d51fbb3442febb6a0cdf361588a-1273932783.eu-central-1.elb.amazonaws.com";
    private static final String TASKS_API = "http://aaccb8ee0a57f4451bacea0f123731c4-432690050.eu-central-1.elb.amazonaws.com";
    
    public Map<String, Object> getProject(String projectId) {
        return restTemplate.getForObject(PROJECTS_API + "/projects/" + projectId, Map.class);
    }
    
    public List<Map<String, Object>> getTasksByProject(String projectId) {
        return restTemplate.getForObject(TASKS_API + "/tasks/project/" + projectId, List.class);
    }
}