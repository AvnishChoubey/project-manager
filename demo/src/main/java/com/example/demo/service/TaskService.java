package com.example.demo.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.enums.Status;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.request.TaskRequest;
import com.example.demo.response.TaskResponse;
import com.example.demo.transformer.ModelToResponse;
import com.example.demo.transformer.RequestToModel;

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

    public ResponseEntity<TaskResponse> updateTask(Long projectId, Long taskId, @RequestBody TaskRequest taskRequest) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

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
    }
}
