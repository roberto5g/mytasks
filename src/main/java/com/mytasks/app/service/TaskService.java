package com.mytasks.app.service;

import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Long taskId);

    TaskResponse createTask(TaskRequest taskRequest);

    TaskResponse updateTask(Long taskId, TaskRequest taskRequest);

    void deleteTask(Long taskId);

}
