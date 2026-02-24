package com.example.backend.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.Comment;
import com.example.backend.model.Task;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.TaskRepository;
import com.example.backend.request.CommentRequest;
import com.example.backend.request.EmailRequest;
import com.example.backend.response.CommentResponse;
import com.example.backend.transformer.ModelToResponse;
import com.example.backend.transformer.RequestToModel;

@Service
public class CommentService {
    @Autowired EmailService emailService;
    @Autowired TaskRepository taskRepository;
    @Autowired CommentRepository commentRepository;

    public ResponseEntity<CommentResponse> getCommentById(Long projectId, Long taskId, Long commentId) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        Comment comment = commentRepository.findByIdAndTaskId(commentId, task.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));

        CommentResponse commentResponse = ModelToResponse.commentToCommentResponse(comment);
        
        return ResponseEntity.ok(commentResponse);
    }

    public ResponseEntity<List<CommentResponse>> getAllComments(Long projectId, Long taskId) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        List<Comment> comments = commentRepository.findAllByTaskId(task.getId());

        List<CommentResponse> commentResponses = comments.stream()
            .map(ModelToResponse::commentToCommentResponse)
            .toList();

        return ResponseEntity.ok(commentResponses);
    }

    public ResponseEntity<CommentResponse> createComment(Long projectId, Long taskId, CommentRequest commentRequest) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));
        
        Comment comment = RequestToModel.commentRequestToComment(commentRequest);
        Comment savedComment = commentRepository.save(comment);

        EmailRequest emailRequest = EmailRequest.builder()
                                                .content("New comment has been added on task id = " + taskId + ". Please checkout.")
                                                .subject("Comment Added")
                                                .toEmail(task.getAssignedTo().getEmail())
                                                .build();
        emailService.sendMail(emailRequest);
        
        URI location = URI.create("/project/" + projectId + "/task/" + taskId + "/comment/" + savedComment.getId());
        return ResponseEntity
                        .created(location)
                        .body(ModelToResponse.commentToCommentResponse(savedComment));
    }

    public ResponseEntity<CommentResponse> updateComment(Long projectId, Long taskId, Long commentId, Long clientId, String commentContent) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        Comment comment = commentRepository.findByIdAndTaskId(commentId, taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));

        if(comment.getCommentedBy().getId() == clientId) {
            comment.setContent(commentContent);
            Comment updatedComment = commentRepository.save(comment);
    
            EmailRequest emailRequest = EmailRequest.builder()
                                                    .content("Some comments has been added on task id = " + taskId + ". Please checkout.")
                                                    .subject("Comment Updated")
                                                    .toEmail(task.getAssignedTo().getEmail())
                                                    .build();
            emailService.sendMail(emailRequest);
    
            return ResponseEntity.ok(ModelToResponse.commentToCommentResponse(updatedComment));
        } else {
            throw new BadRequestException("You are not allowed to modify this comment.");
        }
    }
}
