package com.example.demo.request;

import com.example.demo.model.Task;
import com.example.demo.model.User;

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
