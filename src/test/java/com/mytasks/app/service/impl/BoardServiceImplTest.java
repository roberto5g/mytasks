package com.mytasks.app.service.impl;

import com.mytasks.app.TestBoardFactory;
import com.mytasks.app.TestUserFactory;
import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.exceptions.AccessForbiddenException;
import com.mytasks.app.exceptions.BoardNotFoundException;
import com.mytasks.app.mapper.BoardMapper;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.BoardRepository;
import com.mytasks.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BoardServiceImplTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BoardServiceImpl boardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBoards() {
        List<Board> boardList = new ArrayList<>();
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);
        boardList.add(expectedBoard);

        when(boardRepository.findAll()).thenReturn(boardList);

        List<BoardResponse> boardResponseList = boardService.getAllBoards();

        verify(boardRepository, times(1)).findAll();

        assertEquals(1, boardResponseList.size());
        assertEquals("board1", boardResponseList.get(0).getTitle());
        assertEquals("board1", boardResponseList.get(0).getDescription());
        assertEquals("user1", boardResponseList.get(0).getOwner().getName());

    }

    @Test
    void testGetAllBoardsByOwner() {
        List<Board> boardList = new ArrayList<>();
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);
        boardList.add(expectedBoard);

        when(userService.getUserLogged()).thenReturn(expectedUser);
        when(boardRepository.findByOwnerId(expectedUser.getId())).thenReturn(boardList);

        List<BoardResponse> boardResponseList = boardService.getAllBoardsByOwner();

        verify(userService, times(1)).getUserLogged();

        assertEquals(1, boardResponseList.size());
        assertEquals("board1", boardResponseList.get(0).getTitle());
        assertEquals("board1", boardResponseList.get(0).getDescription());
        assertEquals("user1", boardResponseList.get(0).getOwner().getName());
    }

    @Test
    void testCreateBoard() {
        User expectedUser = TestUserFactory.createTestUser();
        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setTitle("board1");
        boardRequest.setDescription("board1");
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);

        when(userService.getUserLogged()).thenReturn(expectedUser);
        when(boardRepository.save(any())).thenReturn(expectedBoard);

        BoardResponse boardResponse = boardService.createBoard(boardRequest);

        assertEquals("board1", boardResponse.getTitle());
        assertEquals("board1", boardResponse.getDescription());
        assertEquals("user1", boardResponse.getOwner().getName());
    }

    @Test
    void testGetBoardById() {
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);

        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(expectedBoard));

        BoardResponse response = boardService.getBoardById(1L);

        assertNotNull(response);
        assertEquals(expectedBoard.getId(), response.getId());
        assertEquals(expectedBoard.getTitle(), response.getTitle());
        assertEquals(expectedBoard.getDescription(), response.getDescription());
        assertEquals(expectedBoard.getOwner().getId(), response.getOwner().getId());
        assertEquals(expectedBoard.getCreatedAt(), response.getCreatedAt());
    }

    @Test
    void testGetBoardByIdShouldThrowBoardNotFoundException() {
        when(boardRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            boardService.getBoardById(1L);
        } catch (BoardNotFoundException ex) {
            verify(boardRepository, times(1)).findById(1L);
            assertEquals("Board not found with id: " + 1L, ex.getMessage());
        }
    }

    @Test
    void testGetBoardDetailsById() {
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);

        when(boardRepository.findById(1L)).thenReturn(Optional.of(expectedBoard));

        BoardResponseDetails expectedBoardDetails = BoardMapper.toBoardResponseDetails(expectedBoard);

        BoardResponseDetails actualBoardDetails = boardService.getBoardDetailsById(1L);

        verify(boardRepository, times(1)).findById(1L);

        assertEquals(expectedBoardDetails.getId(), actualBoardDetails.getId());
        assertEquals(expectedBoardDetails.getTitle(), actualBoardDetails.getTitle());
        assertEquals(expectedBoardDetails.getDescription(), actualBoardDetails.getDescription());
        assertEquals(expectedBoardDetails.getOwner().getName(), actualBoardDetails.getOwner().getName());
        assertEquals(expectedBoardDetails.getTasks(), actualBoardDetails.getTasks());
        assertEquals(expectedBoardDetails.getCreatedAt(), actualBoardDetails.getCreatedAt());
        assertEquals(expectedBoardDetails.getUpdatedAt(), actualBoardDetails.getUpdatedAt());
    }

    @Test
    void testGetBoardDetailsByIdShouldThrowBoardNotFoundException() {
        when(boardRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            boardService.getBoardDetailsById(1L);
        } catch (BoardNotFoundException ex) {
            verify(boardRepository, times(1)).findById(1L);
            assertEquals("Board not found with id: " + 1L, ex.getMessage());
        }
    }

    @Test
    void testUpdateBoard() {
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);
        String newTitle = "New Board Title";
        String newDescription = "New Board Description";
        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setTitle(newTitle);
        boardRequest.setDescription(newDescription);
        BoardResponse expectedBoardUpdate = TestBoardFactory.updateTestBoard(expectedBoard, boardRequest, expectedUser);

        when(boardRepository.findById(1L)).thenReturn(Optional.of(expectedBoard));
        when(userService.getUserLogged()).thenReturn(expectedUser);
        when(boardService.updateBoard(1L, boardRequest)).thenReturn(expectedBoardUpdate);

        assertEquals(1L, expectedBoardUpdate.getId());
        assertEquals(newTitle, expectedBoardUpdate.getTitle());
        assertEquals(newDescription, expectedBoardUpdate.getDescription());
        assertEquals(expectedUser.getId(), expectedBoardUpdate.getOwner().getId());
    }

    @Test
    void testUpdateBoardShouldThrowBoardNotFoundException() {
        when(boardRepository.findById(1L)).thenReturn(Optional.empty());
        String newTitle = "New Board Title";
        String newDescription = "New Board Description";
        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setTitle(newTitle);
        boardRequest.setDescription(newDescription);
        try {
            boardService.updateBoard(1L, boardRequest);
        } catch (BoardNotFoundException ex) {
            verify(boardRepository, times(1)).findById(1L);
            assertEquals("Board not found with id: " + 1L, ex.getMessage());
        }
    }

    @Test
    void testDeleteBoard() {
        User expectedUser = TestUserFactory.createTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);

        Long boardId = 1L;
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(expectedBoard));
        when(userService.getUserLogged()).thenReturn(expectedUser);

        boardService.deleteBoard(boardId);

        verify(boardRepository).delete(expectedBoard);
    }

    @Test
    void testDeleteBoardByIdShouldThrowBoardNotFoundException() {
        when(boardRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            boardService.deleteBoard(1L);
        } catch (BoardNotFoundException ex) {
            verify(boardRepository, times(1)).findById(1L);
            assertEquals("Board not found with id: " + 1L, ex.getMessage());
        }
    }

    @Test
    void testAccessForbiddenUpdate() {
        User expectedUser = TestUserFactory.createOtherTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);

        Long boardId = 1L;
        String newTitle = "New Board Title";
        String newDescription = "New Board Description";
        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setTitle(newTitle);
        boardRequest.setDescription(newDescription);
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(expectedBoard));
        when(userService.getUserLogged()).thenReturn(TestUserFactory.createTestUser());

        try{
            boardService.updateBoard(boardId, boardRequest);
        } catch (AccessForbiddenException ex){
            verify(boardRepository, times(1)).findById(1L);
            assertEquals("You don't have permission to update this resource.", ex.getMessage());
        }
    }

    @Test
    void testDeleteBoardAccessForbidden() {
        User expectedUser = TestUserFactory.createTestUser();
        User expectedOtherUser = TestUserFactory.createOtherTestUser();
        Board expectedBoard = TestBoardFactory.createTestBoard(expectedUser);

        Long boardId = 1L;
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(expectedBoard));
        when(userService.getUserLogged()).thenReturn(expectedOtherUser);
        try{
            boardService.deleteBoard(boardId);
        } catch (AccessForbiddenException ex){
            verify(boardRepository, times(1)).findById(1L);
            assertEquals("You don't have permission to delete this resource.", ex.getMessage());
        }

    }

}