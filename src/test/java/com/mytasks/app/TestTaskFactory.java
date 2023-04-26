package com.mytasks.app;

import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;

import java.time.LocalDateTime;
import java.util.Date;

public class TestTaskFactory {
    public static Task createTestTask(Board board, User user) {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("task title");
        task.setDescription("task description");
        task.setDueDate(new Date());
        task.setBoard(board);
        task.setCreator(user);
        task.setAssignee(user);
        task.setCreatedAt(LocalDateTime.now());
        return task;
    }
}