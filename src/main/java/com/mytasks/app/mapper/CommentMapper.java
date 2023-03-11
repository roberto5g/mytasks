package com.mytasks.app.mapper;

import com.mytasks.app.dto.CommentDTO;
import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    public static CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setDescription(comment.getDescription());
        dto.setCreator(UserMapper.toDTO(comment.getCreator()));
        dto.setTaskId(comment.getTask().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    public static List<Comment> toEntityList(List<CommentDTO> commentDTOList) {
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

    public static Comment toEntity(CommentDTO commentDTO) {
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
    }

    public static List<CommentDTO> toDTOList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toDTO).collect(Collectors.toList());
    }


}
