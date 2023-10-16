package com.mytasks.app.service.impl;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.exceptions.AccessForbiddenException;
import com.mytasks.app.exceptions.BoardNotFoundException;
import com.mytasks.app.model.Board;
import com.mytasks.app.repository.BoardRepository;
import com.mytasks.app.service.BoardService;
import com.mytasks.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.mytasks.app.mapper.BoardMapper.boardMapperInstance;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserService userService;


    @Override
    public List<BoardResponse> getAllBoards(){
        return boardMapperInstance.toBoardResponseList(boardRepository.findAll());
    }

    @Override
    public List<BoardResponse> getAllBoardsByOwner() {
        return boardMapperInstance.toBoardResponseList(boardRepository.findByOwnerId(userService.getUserLogged().getId()));
    }

    @Override
    public BoardResponse createBoard(BoardRequest boardRequest){
        Board board = boardMapperInstance.boardRequestToBoard(boardRequest);
        board.setOwner(userService.getUserLogged());
        board.setCreatedAt(LocalDateTime.now());

        return boardMapperInstance.toBoardResponse(boardRepository.save(board));
    }

    @Override
    public BoardResponse getBoardById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        return boardMapperInstance.toBoardResponse(board);
    }

    @Override
    public BoardResponseDetails getBoardDetailsById(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        return boardMapperInstance.toBoardResponseDetails(board);
    }

    @Override
    public BoardResponse updateBoard(Long id, BoardRequest boardRequest){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );

        if (!Objects.equals(board.getOwner().getId(), userService.getUserLogged().getId()) && !userService.isAdmin()){
            throw new AccessForbiddenException("update");
        }

        board.setTitle(boardRequest.getTitle());
        board.setDescription(boardRequest.getDescription());
        board.setOwner(userService.getUserLogged());
        board.setUpdatedAt(LocalDateTime.now());

        return boardMapperInstance.toBoardResponse(boardRepository.save(board));

    }

    @Override
    public void deleteBoard(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );

        if (!Objects.equals(board.getOwner().getId(), userService.getUserLogged().getId()) && !userService.isAdmin()){
            throw new AccessForbiddenException("delete");
        }

        boardRepository.delete(board);
    }

}
