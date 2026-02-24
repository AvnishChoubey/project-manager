package com.example.backend.controller;
import com.example.backend.service.CommentService;
import com.example.backend.configuration.CustomUserDetails;
import com.example.backend.request.CommentRequest;
import com.example.backend.response.CommentResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/projects/{projectid}/tasks/{taskId}/comments")
public class CommentController {

    @Autowired CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId) {
        return commentService.getAllComments(projectId, taskId);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @PathVariable("commentId") Long commentId) {
        return commentService.getCommentById(projectId, taskId, commentId);
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> createComment(@PathVariable("projectId") Long projectId, @PathVariable("taskId") Long taskId, @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(projectId, taskId, commentRequest);
    }

    @PutMapping("/{commentId}/update")
    public ResponseEntity<CommentResponse> updateComment(
        @PathVariable("projectId") Long projectId, 
        @PathVariable("taskId") Long taskId, 
        @PathVariable("commentId") Long commentId,
        @AuthenticationPrincipal CustomUserDetails userDetails, 
        @RequestBody String commentContent) {
            Long clientId = userDetails.getUserId();
        return commentService.updateComment(projectId, taskId, commentId, clientId, commentContent);
    }
}