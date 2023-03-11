package com.mytasks.app.service;

import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.exceptions.TaskNotFoundException;
import com.mytasks.app.mapper.CommentMapper;
import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.repository.CommentRepository;
import com.mytasks.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<CommentResponse> getCommentsByTask(Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        List<Comment> comments = commentRepository.findByTaskId(task.getId());
        return comments.stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }
}
