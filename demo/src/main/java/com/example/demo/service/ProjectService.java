package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.request.ProjectRequest;
import com.example.demo.response.ProjectResponse;
import com.example.demo.transformer.ModelToResponse;
import com.example.demo.transformer.RequestToModel;

@Service
public class ProjectService {
    @Autowired ProjectRepository projectRepository;

    public List<ProjectResponse> getAllProjects()
    {
        List<Project> projects = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for(int i=0;i<projects.size();i++)
        {
            projectResponses.add(ModelToResponse.projectToProjectResponse(projects.get(i)));
        }
        return projectResponses;
    }

    public ProjectResponse getProjectById(Long projectId)
    {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found");
        } else {
            return ModelToResponse.projectToProjectResponse(optionalProject.get());
        }
    }

    public ProjectResponse createProject(ProjectRequest projectRequest)
    {
        Project project = RequestToModel.projectRequestToProject(projectRequest);
        Project savedProject = projectRepository.save(project);
        return ModelToResponse.projectToProjectResponse(savedProject);
    }

    public ProjectResponse updateProject(Long projectId, ProjectRequest projectRequest)
    {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found");
        } else {
            Project project = optionalProject.get();
            if(projectRequest.getName() != null) {
                project.setName(projectRequest.getName());
            }
            if(projectRequest.getDescription() != null) {
                project.setDescription(projectRequest.getDescription());
            }
            Project updatedProject = projectRepository.save(project);
            return ModelToResponse.projectToProjectResponse(updatedProject);
        }
    }
}
