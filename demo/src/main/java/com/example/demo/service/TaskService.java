package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.enums.Status;
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
    
    public List<TaskResponse> getAllTasks(Long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            List<Task> tasks = taskRepository.findAllByProjectId(projectId);
            List<TaskResponse> taskResponses = new ArrayList<>();
            for(int i=0;i<tasks.size();i++) {
                taskResponses.add(ModelToResponse.taskToTaskResponse(tasks.get(i)));
            }
            return taskResponses;
        }
    }

    public TaskResponse getTaskById(Long projectId, Long taskId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()) {
                throw new RuntimeException("Task not found with id " + taskId);
            } else {
                Task task = optionalTask.get();
                if(!task.getProject().getId().equals(projectId)) {
                    throw new RuntimeException("Task with id " + taskId + " does not belong to project with id " + projectId);
                } else {
                    return ModelToResponse.taskToTaskResponse(task);
                }
            }
        }
    }

    public TaskResponse createTask(Long projectId, @RequestBody TaskRequest taskRequest) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Project project = optionalProject.get();
            Task task = RequestToModel.taskRequestToTask(taskRequest);
            task.setStatus(Status.TO_BE_PICKED);
            task.setProject(project);
            Task savedTask = taskRepository.save(task);
            return ModelToResponse.taskToTaskResponse(savedTask);
        }
    }

    public TaskResponse updateTask(Long projectId, Long taskId, @RequestBody TaskRequest taskRequest) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()) {
                throw new RuntimeException("Task not found with id " + taskId);
            } else {
                Task task = optionalTask.get();
                if(!task.getProject().getId().equals(projectId)) {
                    throw new RuntimeException("Task with id " + taskId + " does not belong to project with id " + projectId);
                } else {
                    if(!taskRequest.getTitle().isEmpty())
                    task.setTitle(taskRequest.getTitle());
                    
                    if(!taskRequest.getDescription().isEmpty())
                    task.setDescription(taskRequest.getDescription());
                    
                    if(taskRequest.getStatus() != null)
                    task.setStatus(taskRequest.getStatus());

                    Task updatedTask = taskRepository.save(task);
                    return ModelToResponse.taskToTaskResponse(updatedTask);
                }
            }
        }
    }
}
