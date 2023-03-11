package com.mytasks.app.mapper;

import com.mytasks.app.dto.CommentRequest;
import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    public static CommentResponse toCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setDescription(comment.getDescription());
        commentResponse.setCreator(comment.getCreator().getName());
        commentResponse.setTask(comment.getTask().getId());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        return commentResponse;
    }

    /*public static List<Comment> toCommentList(List<CommentRequest> commentDTOList) {
        return commentDTOList.stream().map(
                commentDTO -> {
                    Comment comment = new Comment();
                    comment.setId(commentDTO.getId());
                    comment.setDescription(commentDTO.getDescription());
                    comment.setCreatedAt(commentDTO.getCreatedAt());
                    User creator = new User();
                    creator.setId(commentDTO.getCreator().getId());
                    comment.setCreator(creator);
                    Task task = new Task();
                    task.setId(commentDTO.getTaskId());
                    comment.setTask(task);
                    return comment;
                }).collect(Collectors.toList());
    }
     */

    /*public static Comment toComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setId(commentRequest.getId());
        comment.setDescription(commentDTO.getDescription());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        User creator = new User();
        creator.setId(commentDTO.getCreator().getId());
        comment.setCreator(creator);
        Task task = new Task();
        task.setId(commentDTO.getTaskId());
        comment.setTask(task);
        return comment;
    }
     */

    public static List<CommentResponse> toCommentResponseList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }


    public static Comment toComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setDescription(commentRequest.getDescription());
        return comment;
    }
}
