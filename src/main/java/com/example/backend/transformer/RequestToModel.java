package com.example.backend.transformer;

import com.example.backend.model.Comment;
import com.example.backend.model.Project;
import com.example.backend.model.Task;
import com.example.backend.model.User;
import com.example.backend.request.CommentRequest;
import com.example.backend.request.ProjectRequest;
import com.example.backend.request.TaskRequest;
import com.example.backend.request.UserRequest;

public class RequestToModel {
    
    public static User userRequestToUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .active(userRequest.isActive())
                .role(userRequest.getRole())
                .build();
    }

    public static Comment commentRequestToComment(CommentRequest commentRequest) {
        return Comment.builder()
                .content(commentRequest.getContent())
                .task(commentRequest.getTask())
                .commentedBy(commentRequest.getCommentedBy())
                .build();
    }

    public static Task taskRequestToTask(TaskRequest taskRequest) {
        return Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .createdBy(taskRequest.getCreatedBy())
                .status(taskRequest.getStatus())
                .build();
    }

    public static Project projectRequestToProject(ProjectRequest projectRequest) {
        return Project.builder()
                .name(projectRequest.getName())
                .description(projectRequest.getDescription())
                .admin(projectRequest.getAdmin())
                .build();
    }
}
