package com.mytasks.app.service.impl;

import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.dto.TaskResponse;
import com.mytasks.app.exceptions.AccessForbiddenException;
import com.mytasks.app.exceptions.TaskNotFoundException;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.BoardRepository;
import com.mytasks.app.repository.TaskRepository;
import com.mytasks.app.service.BoardService;
import com.mytasks.app.service.TaskService;
import com.mytasks.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.mytasks.app.mapper.BoardMapper.boardMapperInstance;
import static com.mytasks.app.mapper.TaskMapper.taskMapperInstance;
import static com.mytasks.app.mapper.UserMapper.userMapperInstance;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapperInstance.toDTOList(tasks);
    }

    @Override
    public TaskResponse getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(taskId)
        );
        return taskMapperInstance.toTaskResponse(task);
    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        User creator = userService.getUserLogged();
        User assignee = null;
        if(taskRequest.getAssignee() != null){
            assignee = userMapperInstance.toEntity(userService.getUserById(taskRequest.getAssignee()));
        }

        Task task = taskMapperInstance.toEntity(taskRequest);
        task.setCreator(creator);
        task.setAssignee(assignee);
        task.setBoard(boardMapperInstance.boardResponseToBoardId(boardService.getBoardById(taskRequest.getBoardId())));

        task.setCreatedAt(LocalDateTime.now());
        Task createdTask = taskRepository.save(task);
        return taskMapperInstance.toTaskResponse(createdTask);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        );
        User assignee = null;
        if(taskRequest.getAssignee() != null){
            assignee = userMapperInstance.toEntity(userService.getUserById(taskRequest.getAssignee()));
        }

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setAssignee(assignee);
        task.setBoard(boardMapperInstance.boardResponseToBoardId(boardService.getBoardById(taskRequest.getBoardId())));
        task.setDueDate(taskRequest.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.save(task);
        return taskMapperInstance.toTaskResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(id)
        );

        if (!Objects.equals(task.getCreator().getId(), userService.getUserLogged().getId()) && !userService.isAdmin()){
            throw new AccessForbiddenException("delete");
        }

        taskRepository.delete(task);
    }

}
