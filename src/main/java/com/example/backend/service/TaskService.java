package com.example.backend.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend.enums.Status;
import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Project;
import com.example.backend.model.Task;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.repository.TaskRepository;
import com.example.backend.request.TaskRequest;
import com.example.backend.response.TaskResponse;
import com.example.backend.transformer.ModelToResponse;
import com.example.backend.transformer.RequestToModel;

@Service
public class TaskService {
    @Autowired ProjectRepository projectRepository;
    @Autowired TaskRepository taskRepository;
    
    public ResponseEntity<List<TaskResponse>> getAllTasks(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));

        List<Task> tasks = taskRepository.findAllByProjectId(project.getId());
        
        List<TaskResponse> taskResponses = new ArrayList<>();
        for(int i=0;i<tasks.size();i++) {
            taskResponses.add(ModelToResponse.taskToTaskResponse(tasks.get(i)));
        }
        
        return ResponseEntity.ok(taskResponses);
    }

    public ResponseEntity<TaskResponse> getTaskById(Long projectId, Long taskId) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        TaskResponse taskResponse = ModelToResponse.taskToTaskResponse(task);
        
        return ResponseEntity.ok(taskResponse);
    }

    public ResponseEntity<TaskResponse> createTask(Long projectId, @RequestBody TaskRequest taskRequest) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        
        Task task = RequestToModel.taskRequestToTask(taskRequest);
        
        task.setStatus(Status.TO_BE_PICKED);
        task.setProject(project);

        Task savedTask = taskRepository.save(task);
        TaskResponse taskResponse = ModelToResponse.taskToTaskResponse(savedTask);
        
        URI location = URI.create("/project/" + projectId + "/task/" + savedTask.getId());
        
        return ResponseEntity.created(location).body(taskResponse);
    }

    public ResponseEntity<TaskResponse> updateTask(Long projectId, Long taskId, Long clientId, @RequestBody TaskRequest taskRequest) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
            
        if(task.getAssignedTo().getId() == clientId) {
            if (StringUtils.hasText(taskRequest.getTitle())) {
                task.setTitle(taskRequest.getTitle());
            }
    
            if (StringUtils.hasText(taskRequest.getDescription())) {
                task.setDescription(taskRequest.getDescription());
            }
    
            if (taskRequest.getStatus() != null) {
                task.setStatus(taskRequest.getStatus());
            }
            Task updatedTask = taskRepository.save(task);
            TaskResponse taskResponse = ModelToResponse.taskToTaskResponse(updatedTask);
            
            return ResponseEntity.ok(taskResponse);
        } else {
            throw new BadRequestException("You are not allowed to make changes.");
        }
    }
}
