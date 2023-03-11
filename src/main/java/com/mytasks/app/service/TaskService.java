package com.mytasks.app.service;

import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.dto.TaskResponse;
import com.mytasks.app.exceptions.EntityNotFoundException;
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
import java.util.Optional;

@Service
public class TaskService {

    private static final String TASK_NOT_FOUND = "Task not found with id: ";

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
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));
        return TaskMapper.toTaskResponse(task);
    }
    public TaskResponse createTask(TaskRequest taskRequest) {
        User creator = userRepository.findById(taskRequest.getCreator())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: "+taskRequest.getCreator()));
        User assignee = null;
        if(taskRequest.getAssignee() != null){
            assignee = userRepository.findById(taskRequest.getAssignee())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: "+taskRequest.getAssignee()));
        }
        Board board = boardRepository.findById(taskRequest.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found with id: "+taskRequest.getBoardId()));
        Task task = TaskMapper.toEntity(taskRequest);
        task.setCreator(creator);
        task.setAssignee(assignee);
        task.setBoard(board);

        task.setCreatedAt(LocalDateTime.now());
        Task createdTask = taskRepository.save(task);
        return TaskMapper.toTaskResponse(createdTask);
    }

    public TaskResponse updateTask(Long taskId, TaskResponse taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(TASK_NOT_FOUND + taskId));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toTaskResponse(updatedTask);
    }

    public void deleteTask(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isPresent()) {
            taskRepository.delete(optionalTask.get());
        } else {
            throw new EntityNotFoundException(TASK_NOT_FOUND + taskId);
        }
    }

}
