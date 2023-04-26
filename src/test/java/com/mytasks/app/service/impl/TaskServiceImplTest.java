package com.mytasks.app.service.impl;

import com.mytasks.app.TestBoardFactory;
import com.mytasks.app.TestTaskFactory;
import com.mytasks.app.TestUserFactory;
import com.mytasks.app.dto.TaskRequest;
import com.mytasks.app.dto.TaskResponse;
import com.mytasks.app.mapper.BoardMapper;
import com.mytasks.app.mapper.UserMapper;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.BoardRepository;
import com.mytasks.app.repository.TaskRepository;
import com.mytasks.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TaskServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private BoardServiceImpl boardService;

    @InjectMocks
    private TaskServiceImpl taskService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        List<Task> taskList = new ArrayList<>();
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);
        Task expectedTask = TestTaskFactory.createTestTask(expectedBoard, expectedUser);
        taskList.add(expectedTask);

        when(taskRepository.findAll()).thenReturn(taskList);
        List<TaskResponse> taskResponseList = taskService.getAllTasks();

        verify(taskRepository, times(1)).findAll();
        assertEquals(1, taskResponseList.size());
        assertEquals("task title", taskResponseList.get(0).getTitle());
        assertEquals("task description", taskResponseList.get(0).getDescription());
        assertEquals("user1", taskResponseList.get(0).getCreator().getName());

    }

    @Test
    void testGetTaskById() {
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);
        Task expectedTask = TestTaskFactory.createTestTask(expectedBoard, expectedUser);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(expectedTask));

        TaskResponse response = taskService.getTaskById(1L);

        assertNotNull(response);
        assertEquals(expectedTask.getId(), response.getId());
        assertEquals(expectedTask.getTitle(), response.getTitle());
        assertEquals(expectedTask.getDescription(), response.getDescription());
        assertEquals(expectedTask.getCreator().getName(), response.getCreator().getName());
        assertEquals(expectedTask.getCreatedAt(), response.getCreatedAt());

    }

    @Test
    void testCreateTask() {
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);
        Task expectedTask = TestTaskFactory.createTestTask(expectedBoard, expectedUser);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 23, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dueDateTask = calendar.getTime();

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setBoardId(1L);
        taskRequest.setAssignee(expectedUser.getId());
        taskRequest.setTitle("task title");
        taskRequest.setDescription("task description");
        taskRequest.setDueDate(dueDateTask);

        when(userService.getUserLogged()).thenReturn(expectedUser);
        when(userService.getUserById(any())).thenReturn(UserMapper.toUserResponse(expectedUser));
        when(taskRepository.save(any())).thenReturn(expectedTask);
        when(boardService.getBoardById(anyLong())).thenReturn(BoardMapper.toBoardResponse(expectedTask.getBoard()));

        TaskResponse taskResponse = taskService.createTask(taskRequest);

        assertEquals("task title", taskResponse.getTitle());
        assertEquals("task description", taskResponse.getDescription());
        assertEquals("user1", taskResponse.getCreator().getName());
        assertEquals("user1", taskResponse.getAssignee().getName());
        int result = dueDateTask.compareTo(taskResponse.getDueDate());
        assertTrue(result < 0);

    }

    @Test
    void testUpdateTask() {
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);
        Task expectedTask = TestTaskFactory.createTestTask(expectedBoard, expectedUser);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.APRIL, 23, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dueDateTask = calendar.getTime();

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setBoardId(expectedBoard.getId());
        taskRequest.setAssignee(expectedUser.getId());
        taskRequest.setTitle("task title2");
        taskRequest.setDescription("task description2");
        taskRequest.setDueDate(dueDateTask);

        when(userService.getUserById(taskRequest.getAssignee())).thenReturn(UserMapper.toUserResponse(expectedUser));
        when(taskRepository.findById(expectedTask.getId())).thenReturn(Optional.of(expectedTask));
        when(taskRepository.save(expectedTask)).thenReturn(expectedTask);
        when(boardService.getBoardById(taskRequest.getBoardId())).thenReturn(BoardMapper.toBoardResponse(expectedTask.getBoard()));

        TaskResponse updatedTaskResponse = taskService.updateTask(expectedTask.getId(), taskRequest);

        assertNotNull(updatedTaskResponse);
        assertEquals(expectedTask.getId(), updatedTaskResponse.getId());
        assertEquals(taskRequest.getTitle(), updatedTaskResponse.getTitle());
        assertEquals(taskRequest.getDescription(), updatedTaskResponse.getDescription());
        assertEquals(taskRequest.getAssignee(), updatedTaskResponse.getAssignee().getId());
        assertEquals(taskRequest.getBoardId(), updatedTaskResponse.getBoardId());
        assertEquals(taskRequest.getDueDate(), updatedTaskResponse.getDueDate());
    }

    @Test
    void deleteTask() {
    }
}