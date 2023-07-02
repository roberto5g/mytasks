package com.mytasks.app.mapper;

import com.mytasks.app.dto.CommentRequest;
import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.model.Comment;

import java.util.List;

public class CommentMapper {

    private CommentMapper(){}

    public static CommentResponse toCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setDescription(comment.getDescription());
        commentResponse.setCreator(comment.getCreator().getName());
        commentResponse.setTask(comment.getTask().getId());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        return commentResponse;
    }

    public static List<CommentResponse> toCommentResponseList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentResponse).toList();
    }


    public static Comment toComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setDescription(commentRequest.getDescription());
        return comment;
    }
}
