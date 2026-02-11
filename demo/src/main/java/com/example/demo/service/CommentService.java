package com.example.demo.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.request.CommentRequest;
import com.example.demo.request.EmailRequest;
import com.example.demo.response.CommentResponse;
import com.example.demo.transformer.ModelToResponse;
import com.example.demo.transformer.RequestToModel;

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

    public ResponseEntity<CommentResponse> updateComment(Long projectId, Long taskId, Long commentId, String commentContent) {
        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        Comment comment = commentRepository.findByIdAndTaskId(commentId, taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));

        comment.setContent(commentContent);
        Comment updatedComment = commentRepository.save(comment);

        EmailRequest emailRequest = EmailRequest.builder()
                                                .content("Some comments has been added on task id = " + taskId + ". Please checkout.")
                                                .subject("Comment Updated")
                                                .toEmail(task.getAssignedTo().getEmail())
                                                .build();
        emailService.sendMail(emailRequest);

        return ResponseEntity.ok(ModelToResponse.commentToCommentResponse(updatedComment));
    }
}
