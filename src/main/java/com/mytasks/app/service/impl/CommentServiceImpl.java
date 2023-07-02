package com.mytasks.app.service.impl;

import com.mytasks.app.dto.CommentRequest;
import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.exceptions.AccessForbiddenException;
import com.mytasks.app.exceptions.CommentNotFoundException;
import com.mytasks.app.exceptions.TaskNotFoundException;
import com.mytasks.app.mapper.CommentMapper;
import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.repository.CommentRepository;
import com.mytasks.app.repository.TaskRepository;
import com.mytasks.app.service.CommentService;
import com.mytasks.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<CommentResponse> getCommentsByTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        );

        List<Comment> comments = commentRepository.findByTaskId(task.getId());

        return comments.stream().map(CommentMapper::toCommentResponse).toList();
    }

    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        Task task = taskRepository.findById(commentRequest.getTaskId()).orElseThrow(
                () -> new TaskNotFoundException(commentRequest.getTaskId())
        );

        Comment comment = CommentMapper.toComment(commentRequest);
        comment.setTask(task);
        comment.setCreator(userService.getUserLogged());
        comment.setCreatedAt(LocalDateTime.now());

        return CommentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public CommentResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException(id)
        );

        if (!Objects.equals(comment.getCreator().getId(), userService.getUserLogged().getId())){
            throw new AccessForbiddenException("edit");
        }

        comment.setDescription(commentRequest.getDescription());
        comment.setUpdatedAt(LocalDateTime.now());

        return CommentMapper.toCommentResponse(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        if (!Objects.equals(comment.getCreator().getId(), userService.getUserLogged().getId()) && !userService.isAdmin()){
            throw new AccessForbiddenException("delete");
        }

        commentRepository.delete(comment);
    }
}
