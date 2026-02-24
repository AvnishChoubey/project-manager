package com.example.backend.transformer;

import com.example.backend.model.Comment;
import com.example.backend.model.Project;
import com.example.backend.model.Task;
import com.example.backend.model.User;
import com.example.backend.response.CommentResponse;
import com.example.backend.response.ProjectResponse;
import com.example.backend.response.TaskResponse;
import com.example.backend.response.UserResponse;

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
