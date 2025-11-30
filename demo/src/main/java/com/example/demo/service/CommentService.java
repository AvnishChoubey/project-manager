package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.request.CommentRequest;
import com.example.demo.response.CommentResponse;
import com.example.demo.transformer.ModelToResponse;
import com.example.demo.transformer.RequestToModel;

@Service
public class CommentService {
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


    public List<CommentResponse> getAllComments(Long projectId, Long taskId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new RuntimeException("Project not found with id " + projectId);
        } else {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if(optionalTask.isEmpty()) {
                throw new RuntimeException("Task not found with id " + taskId);
            } else {
                List<Comment> comments = commentRepository.findAll();
                List<CommentResponse> commentResponses = comments.stream()
                    .filter(comment -> comment.getTask().getId().equals(taskId))
                    .map(ModelToResponse::commentToCommentResponse)
                    .toList();
                return commentResponses;
            }
        }
    }

    public CommentResponse createComment(Long projectId, Long taskId, CommentRequest commentRequest) {
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
                return ModelToResponse.commentToCommentResponse(savedComment);
            }
        }
    }

    public CommentResponse updateComment(Long projectId, Long taskId, Long commentId, String commentContent) {
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
                    return ModelToResponse.commentToCommentResponse(updatedComment);
                }
            }
        }
    }
}
