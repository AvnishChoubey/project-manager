package com.example.backend.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Project;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.request.ProjectRequest;
import com.example.backend.response.ProjectResponse;
import com.example.backend.transformer.ModelToResponse;
import com.example.backend.transformer.RequestToModel;

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
