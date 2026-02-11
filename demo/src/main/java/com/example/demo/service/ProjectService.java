package com.example.demo.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.request.ProjectRequest;
import com.example.demo.response.ProjectResponse;
import com.example.demo.transformer.ModelToResponse;
import com.example.demo.transformer.RequestToModel;

@Service
public class ProjectService {
    @Autowired ProjectRepository projectRepository;

    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        List<ProjectResponse> projectResponses = new ArrayList<>();
        for(int i=0;i<projects.size();i++) {
            projectResponses.add(ModelToResponse.projectToProjectResponse(projects.get(i)));
        }
        
        return ResponseEntity.ok(projectResponses);
    }

    public ResponseEntity<ProjectResponse> getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        ProjectResponse projectResponse = ModelToResponse.projectToProjectResponse(project);

        return ResponseEntity.ok(projectResponse);
    }

    public ResponseEntity<ProjectResponse> createProject(ProjectRequest projectRequest) {
        Project project = RequestToModel.projectRequestToProject(projectRequest);
        
        Project savedProject = projectRepository.save(project);
        
        URI location = URI.create("/projects/" + savedProject.getId());
        
        ProjectResponse projectResponse = ModelToResponse.projectToProjectResponse(savedProject);
        
        return ResponseEntity.created(location).body(projectResponse);
    }

    public ResponseEntity<ProjectResponse> updateProject(Long projectId, ProjectRequest projectRequest) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
    
        if(StringUtils.hasText(projectRequest.getName())) {
            project.setName(projectRequest.getName());
        }

        if(StringUtils.hasText(projectRequest.getDescription())) {
            project.setDescription(projectRequest.getDescription());
        }
        
        Project updatedProject = projectRepository.save(project);
        
        ProjectResponse projectResponse = ModelToResponse.projectToProjectResponse(updatedProject);
        
        return ResponseEntity.ok(projectResponse);
    }
}
