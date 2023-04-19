package com.mytasks.app.service.impl;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.exceptions.AccessForbiddenException;
import com.mytasks.app.exceptions.BoardNotFoundException;
import com.mytasks.app.mapper.BoardMapper;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.BoardRepository;
import com.mytasks.app.service.BoardService;
import com.mytasks.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<BoardResponse> getAllBoards(){
        return BoardMapper.toBoardResponseList(boardRepository.findAll());
    }

    @Override
    public List<BoardResponse> getAllBoardsByOwner() {
        return BoardMapper.toBoardResponseList(boardRepository.findByOwnerId(userService.getUserLogged().getId()));
    }

    @Override
    public BoardResponse createBoard(BoardRequest boardRequest){
        User owner = userService.getUserLogged();
        Board board = BoardMapper.BoardRequestToBoard(boardRequest);
        board.setOwner(owner);
        board.setCreatedAt(LocalDateTime.now());
        return BoardMapper.toBoardResponse(boardRepository.save(board));
    }

    @Override
    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        return BoardMapper.toBoardResponse(board);
    }

    @Override
    public BoardResponseDetails getBoardDetailsById(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        return BoardMapper.toBoardResponseDetails(board);
    }

    @Override
    public BoardResponse updateBoard(Long id, BoardRequest boardRequest){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        User owner = userService.getUserLogged();
        if (!board.getOwner().equals(owner)){
            throw new AccessForbiddenException("update");
        }
        board.setTitle(boardRequest.getTitle());
        board.setDescription(boardRequest.getDescription());
        board.setOwner(owner);
        board.setUpdatedAt(LocalDateTime.now());
        return BoardMapper.toBoardResponse(boardRepository.save(board));
    }

    @Override
    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        User owner = userService.getUserLogged();
        if (!board.getOwner().equals(owner)){
            throw new AccessForbiddenException("delete");
        }
        boardRepository.delete(board);
    }

}
