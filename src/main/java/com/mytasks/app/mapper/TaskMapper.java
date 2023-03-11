package com.mytasks.app.mapper;

import com.mytasks.app.dto.CommentDTO;
import com.mytasks.app.dto.TaskDTO;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setCreator(UserMapper.toDTO(task.getCreator()));
        taskDTO.setAssignee(UserMapper.toDTO(task.getAssignee()));
        taskDTO.setBoardId(task.getBoard().getId());
        taskDTO.setComments(CommentMapper.toDTOList(task.getComments()));
        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());
        return taskDTO;
    }

    public static Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        Board board = new Board();
        board.setId(taskDTO.getBoardId());
        task.setBoard(board);
        User creator = new User();
        creator.setId(taskDTO.getCreator().getId());
        task.setCreator(creator);
        User assignee = new User();
        assignee.setId(taskDTO.getAssignee().getId());
        task.setAssignee(assignee);

        List<CommentDTO> commentDTOList = taskDTO.getComments();
        List<Comment> commentList = commentDTOList.stream().map(CommentMapper::toEntity).toList();
        task.setComments(commentList);
        return task;
    }


    public static List<TaskDTO> toDTOList(List<Task> tasks) {
        return tasks.stream().map(TaskMapper::toDTO).collect(Collectors.toList());
    }

}
