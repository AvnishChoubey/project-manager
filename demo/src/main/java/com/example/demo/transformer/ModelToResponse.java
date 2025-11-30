package com.example.demo.transformer;

import com.example.demo.model.Comment;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.response.CommentResponse;
import com.example.demo.response.ProjectResponse;
import com.example.demo.response.TaskResponse;
import com.example.demo.response.UserResponse;

public class ModelToResponse {
    public static UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                        .employeeId(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build();
    }

    public static ProjectResponse projectToProjectResponse(Project project) {
        return ProjectResponse.builder()
                        .id(project.getId())
                        .name(project.getName())
                        .admin(userToUserResponse(project.getAdmin()))
                        .description(project.getDescription())
                        .build();
    }

    public static CommentResponse commentToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .task(comment.getTask())
                        .commentedBy(userToUserResponse(comment.getCommentedBy()))
                        .build();
    }

    public static TaskResponse taskToTaskResponse(Task task) {
        return TaskResponse.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .createdBy(userToUserResponse(task.getCreatedBy()))
                        .assignedTo(userToUserResponse(task.getAssignedTo()))
                        .project(projectToProjectResponse(task.getProject()))
                        .build();
    }
}
