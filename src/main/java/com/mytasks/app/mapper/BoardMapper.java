package com.mytasks.app.mapper;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.model.Board;

import java.util.List;
import java.util.stream.Collectors;

public class BoardMapper {

    public static BoardRequest toBoardRequest(Board board) {
        BoardRequest boardDTO = new BoardRequest();
        boardDTO.setTitle(board.getTitle());
        boardDTO.setDescription(board.getDescription());
        boardDTO.setOwner(boardDTO.getOwner());
        return boardDTO;
    }

    public static BoardResponse toBoardResponse(Board board) {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setDescription(board.getDescription());
        boardResponse.setOwner(UserMapper.toUserResponse(board.getOwner()));
        boardResponse.setCreatedAt(board.getCreatedAt());
        boardResponse.setUpdatedAt(board.getUpdatedAt());
        return boardResponse;
    }

    public static BoardResponseDetails toBoardResponseDetails(Board board) {
        BoardResponseDetails boardDetails = new BoardResponseDetails();
        boardDetails.setId(board.getId());
        boardDetails.setTitle(board.getTitle());
        boardDetails.setDescription(board.getDescription());
        boardDetails.setOwner(UserMapper.toUserResponse(board.getOwner()));
        boardDetails.setTasks(TaskMapper.toDTOList(board.getTasks()));
        boardDetails.setCreatedAt(board.getCreatedAt());
        boardDetails.setUpdatedAt(board.getUpdatedAt());
        return boardDetails;
    }

    public static Board toEntity(BoardRequest boardDTO) {
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setDescription(boardDTO.getDescription());
        return board;
    }

    public static List<BoardResponse> toBoardResponseList(List<Board> boards) {
        return boards.stream().map(BoardMapper::toBoardResponse).collect(Collectors.toList());
    }
}

