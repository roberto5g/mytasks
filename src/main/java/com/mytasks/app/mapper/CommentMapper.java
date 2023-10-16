package com.mytasks.app.mapper;

import com.mytasks.app.dto.CommentRequest;
import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CommentMapper {

    CommentMapper commentMapperInstance = Mappers.getMapper(CommentMapper.class);

    CommentResponse toCommentResponse(Comment comment);

    default Long map(Task task) {
        return task != null ? task.getId() : null;
    }

    default String map(User user) {
        return user != null ? user.getName() : null;
    }

    Comment toComment(CommentRequest commentRequest);

    default List<CommentResponse> toCommentResponseList(List<Comment> comments) {
        List<CommentResponse> list = new ArrayList<>();
        comments.forEach(d->list.add(toCommentResponse(d)));
        return list;
    }

}
