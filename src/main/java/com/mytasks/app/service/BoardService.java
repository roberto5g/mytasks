package com.mytasks.app.service;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;

import java.util.List;


public interface BoardService {

    List<BoardResponse> getAllBoards();

    BoardResponse createBoard(BoardRequest boardRequest);

    BoardResponseDetails getBoardById(Long id);

    BoardResponse updateBoard(Long id, BoardRequest boardRequest);

    void deleteBoard(Long boardId);

}
