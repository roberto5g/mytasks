package com.mytasks.app.mapper;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.model.Board;

import java.util.List;
import java.util.stream.Collectors;

public class BoardMapper {

    public static BoardResponse toBoardResponse(Board board) {
        if(board == null) {
            return null;
        }
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setDescription(board.getDescription());
        boardResponse.setOwner(UserMapper.toUserResponseTask(board.getOwner()));
        boardResponse.setCreatedAt(board.getCreatedAt());
        boardResponse.setUpdatedAt(board.getUpdatedAt());
        return boardResponse;
    }

    public static BoardResponseDetails toBoardResponseDetails(Board board) {
        BoardResponseDetails boardDetails = new BoardResponseDetails();
        boardDetails.setId(board.getId());
        boardDetails.setTitle(board.getTitle());
        boardDetails.setDescription(board.getDescription());
        boardDetails.setOwner(UserMapper.toUserResponseTask(board.getOwner()));
        if (board.getTasks() != null) {
            boardDetails.setTasks(TaskMapper.toDTOList(board.getTasks()));
        }
        boardDetails.setCreatedAt(board.getCreatedAt());
        boardDetails.setUpdatedAt(board.getUpdatedAt());
        return boardDetails;
    }

    public static Board BoardRequestToBoard(BoardRequest boardRequest) {
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setDescription(boardRequest.getDescription());
        return board;
    }

    public static Board BoardResponseToBoardId(BoardResponse boardResponse) {
        Board board = new Board();
        board.setId(boardResponse.getId());
        return board;
    }

    public static List<BoardResponse> toBoardResponseList(List<Board> boards) {
        return boards.stream().map(BoardMapper::toBoardResponse).collect(Collectors.toList());
    }
}

