package com.mytasks.app.service;

import com.mytasks.app.dto.CommentRequest;
import com.mytasks.app.dto.CommentResponse;

import java.util.List;


public interface CommentService {
    
    List<CommentResponse> getCommentsByTask(Long taskId);

    CommentResponse createComment(CommentRequest commentRequest);

    CommentResponse updateComment(Long commentId, CommentRequest commentRequest);

    void deleteComment(Long commentId);

}
