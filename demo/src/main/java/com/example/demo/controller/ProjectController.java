package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.ProjectRequest;
import com.example.demo.response.ProjectResponse;
import com.example.demo.service.ProjectService;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    @Autowired ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable("projectId") Long projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @PutMapping("/{projectId}/update")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable("projectId") Long projectId, @RequestBody ProjectRequest projectRequest) {
        return projectService.updateProject(projectId, projectRequest);
    }
}