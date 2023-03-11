package com.mytasks.app.service;

import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.dto.TaskResponse;
import com.mytasks.app.exceptions.BoardNotFoundException;
import com.mytasks.app.exceptions.TaskNotFoundException;
import com.mytasks.app.exceptions.UserNotFoundException;
import com.mytasks.app.mapper.TaskMapper;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.BoardRepository;
import com.mytasks.app.repository.TaskRepository;
import com.mytasks.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return TaskMapper.toDTOList(tasks);
    }

    public TaskResponse getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        return TaskMapper.toTaskResponse(task);
    }
    public TaskResponse createTask(TaskRequest taskRequest) {
        User creator = userRepository.findById(taskRequest.getCreator())
                .orElseThrow(() -> new UserNotFoundException(taskRequest.getCreator()));
        User assignee = null;
        if(taskRequest.getAssignee() != null){
            assignee = userRepository.findById(taskRequest.getAssignee())
                    .orElseThrow(() -> new UserNotFoundException(taskRequest.getAssignee()));
        }
        Board board = boardRepository.findById(taskRequest.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(taskRequest.getBoardId()));
        Task task = TaskMapper.toEntity(taskRequest);
        task.setCreator(creator);
        task.setAssignee(assignee);
        task.setBoard(board);

        task.setCreatedAt(LocalDateTime.now());
        Task createdTask = taskRepository.save(task);
        return TaskMapper.toTaskResponse(createdTask);
    }

    public TaskResponse updateTask(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        User assignee = null;
        if(taskRequest.getAssignee() != null){
            assignee = userRepository.findById(taskRequest.getAssignee())
                    .orElseThrow(() -> new UserNotFoundException(taskRequest.getAssignee()));
        }
        Board board = boardRepository.findById(taskRequest.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException(taskRequest.getBoardId()));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setAssignee(assignee);
        task.setBoard(board);
        task.setDueDate(taskRequest.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toTaskResponse(updatedTask);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        taskRepository.delete(task);
    }

}
