package com.mytasks.app.service.impl;

import com.mytasks.app.dto.CommentRequest;
import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.exceptions.AccessForbiddenException;
import com.mytasks.app.exceptions.CommentNotFoundException;
import com.mytasks.app.exceptions.TaskNotFoundException;
import com.mytasks.app.exceptions.UserNotFoundException;
import com.mytasks.app.mapper.CommentMapper;
import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.CommentRepository;
import com.mytasks.app.repository.TaskRepository;
import com.mytasks.app.repository.UserRepository;
import com.mytasks.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CommentResponse> getCommentsByTask(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(taskId)
        );
        List<Comment> comments = commentRepository.findByTaskId(task.getId());
        return comments.stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }

    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        Task task = taskRepository.findById(commentRequest.getTaskId()).orElseThrow(
                () -> new TaskNotFoundException(commentRequest.getTaskId())
        );
        //TODO I will change it
        Long userId = 1L;
        User creator = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+userId)
        );
        Comment comment = CommentMapper.toComment(commentRequest);
        comment.setTask(task);
        comment.setCreator(creator);
        comment.setCreatedAt(LocalDateTime.now());
        return CommentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(commentId)
        );
        //TODO I will change it
        Long userId = 1L;
        User creator = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+userId)
        );
        if (!comment.getCreator().equals(creator)){
            throw new AccessForbiddenException("edit");
        }
        comment.setDescription(commentRequest.getDescription());
        comment.setUpdatedAt(LocalDateTime.now());
        return CommentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        //TODO I will change it
        Long userId = 1L;
        User creator = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+userId)
        );
        if (!comment.getCreator().equals(creator)){
            throw new AccessForbiddenException("delete");
        }
        commentRepository.delete(comment);
    }
}