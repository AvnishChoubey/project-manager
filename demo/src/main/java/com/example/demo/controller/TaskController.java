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

import com.example.demo.request.TaskRequest;
import com.example.demo.response.TaskResponse;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskService;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/tasks")
public class TaskController {
    @Autowired ProjectService projectService;
    @Autowired TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@PathVariable("projectId") Long projectId) {
        return taskService.getAllTasks(projectId);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        return  taskService.getTaskById(projectId, taskId);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@PathVariable("projectId") Long projectId, @RequestBody TaskRequest  taskRequest) {
        return  taskService.createTask(projectId, taskRequest);
    }

    @PutMapping("/{taskId}/update")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @RequestBody TaskRequest  taskRequest) {
        return  taskService.updateTask(projectId, taskId, taskRequest);
    }
}