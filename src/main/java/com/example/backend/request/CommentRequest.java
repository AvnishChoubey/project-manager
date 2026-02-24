package com.example.backend.request;

import com.example.backend.model.Task;
import com.example.backend.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String content;
    private Task task;
    private User commentedBy;
}
