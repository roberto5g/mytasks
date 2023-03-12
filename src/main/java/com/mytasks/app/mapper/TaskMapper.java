package com.mytasks.app.mapper;

import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.dto.TaskResponse;
import com.mytasks.app.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskResponse toTaskResponse(Task task) {
        TaskResponse taskDTO = new TaskResponse();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDueDate(task.getDueDate());
        taskDTO.setCreator(UserMapper.toUserResponse(task.getCreator()));
        taskDTO.setAssignee(task.getAssignee() != null ? UserMapper.toUserResponse(task.getAssignee()) : null);
        taskDTO.setBoardId(task.getBoard().getId());
        taskDTO.setComments(task.getComments() != null ? CommentMapper.toCommentResponseList(task.getComments()) : null);
        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setUpdatedAt(task.getUpdatedAt());
        return taskDTO;
    }

    public static Task toEntity(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        return task;
    }

    public static List<TaskResponse> toDTOList(List<Task> tasks) {
        return tasks.stream().map(TaskMapper::toTaskResponse).collect(Collectors.toList());
    }

}
