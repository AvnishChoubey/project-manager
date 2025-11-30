package com.example.demo.response;

import com.example.demo.enums.Status;

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
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private ProjectResponse project;
    private UserResponse createdBy;
    private UserResponse assignedTo;
    private Status status;
}
