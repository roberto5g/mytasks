package com.mytasks.app;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.UserResponseTask;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.User;

import java.time.LocalDateTime;

public class TestBoardFactory {
    public static Board createTestBoard(User owner) {
        Board board = new Board();
        board.setId(1L);
        board.setTitle("board1");
        board.setDescription("board1");
        board.setOwner(owner);
        board.setCreatedAt(LocalDateTime.now());
        return board;
    }

    public static BoardResponse updateTestBoard(Board board, BoardRequest boardRequest, User owner) {
        BoardResponse boardResponse = new BoardResponse();
        UserResponseTask userResponseTask = new UserResponseTask();
        userResponseTask.setId(owner.getId());
        userResponseTask.setName(owner.getName());
        userResponseTask.setEmail(owner.getEmail());
        board.setId(1L);
        board.setTitle(boardRequest.getTitle());
        board.setDescription(boardRequest.getDescription());
        board.setOwner(owner);
        board.setUpdatedAt(LocalDateTime.now());
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setDescription(board.getDescription());
        boardResponse.setOwner(userResponseTask);
        boardResponse.setCreatedAt(board.getCreatedAt());
        boardResponse.setUpdatedAt(board.getUpdatedAt());

        return boardResponse;
    }
}