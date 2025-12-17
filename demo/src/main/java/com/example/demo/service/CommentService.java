package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.request.CommentRequest;
import com.example.demo.request.EmailRequest;
import com.example.demo.response.CommentResponse;
import com.example.demo.transformer.ModelToResponse;
import com.example.demo.transformer.RequestToModel;

@Service
public class CommentService {
    @Autowired EmailService emailService;
    @Autowired ProjectRepository projectRepository;
    @Autowired TaskRepository taskRepository;
    @Autowired CommentRepository commentRepository;

    public CommentResponse getCommentById(Long projectId, Long taskId, Long commentId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()) {
                throw new RuntimeException("Task not found with id " + taskId);
            } else {
                Optional<Comment> optionalComment = commentRepository.findById(commentId);
                if(optionalComment.isEmpty()) {
                    throw new RuntimeException("Comment not found with id " + commentId);
                } else {
                    return ModelToResponse.commentToCommentResponse(optionalComment.get());
                }
            }
        }
    }


    public ResponseEntity<List<CommentResponse>> getAllComments(Long projectId, Long taskId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()) {
                throw new RuntimeException("Task not found with id " + taskId);
            } else {
                List<Comment> comments = commentRepository.findAllByTaskId(taskId);
                List<CommentResponse> commentResponses = comments.stream()
                    .map(ModelToResponse::commentToCommentResponse)
                    .toList();
                return ResponseEntity.ok(commentResponses);
            }
        }
    }

    public ResponseEntity<CommentResponse> createComment(Long projectId, Long taskId, CommentRequest commentRequest) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()) {
                throw new RuntimeException("Task not found with id " + taskId);
            } else {
                Comment comment = RequestToModel.commentRequestToComment(commentRequest);
                Comment savedComment = commentRepository.save(comment);
                EmailRequest emailRequest = EmailRequest.builder()
                                                        .content("New comment has been added on task id=" + taskId + ". Please checkout.")
                                                        .subject("Comment Added")
                                                        .toEmail(optionalTask.get().getAssignedTo().getEmail())
                                                        .build();
                emailService.sendMail(emailRequest);
                return ResponseEntity.ok(ModelToResponse.commentToCommentResponse(savedComment));
            }
        }
    }

    public ResponseEntity<CommentResponse> updateComment(Long projectId, Long taskId, Long commentId, String commentContent) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()) {
                throw new RuntimeException("Task not found with id " + taskId);
            } else {
                Optional<Comment> optionalComment = commentRepository.findById(commentId);
                if(optionalComment.isEmpty()) {
                    throw new RuntimeException("Comment not found with id " + commentId);
                } else {
                    Comment comment = optionalComment.get();
                    comment.setContent(commentContent);
                    Comment updatedComment = commentRepository.save(comment);
                    return ResponseEntity.ok(ModelToResponse.commentToCommentResponse(updatedComment));
                }
            }
        }
    }
}
