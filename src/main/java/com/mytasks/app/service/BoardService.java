package com.mytasks.app.service;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.exceptions.EntityNotFoundException;
import com.mytasks.app.mapper.BoardMapper;
import com.mytasks.app.model.Board;
import com.mytasks.app.model.User;
import com.mytasks.app.repository.BoardRepository;
import com.mytasks.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public List<BoardResponse> getAllBoards(){
        List<Board> boards = boardRepository.findAll();
        return BoardMapper.toBoardResponseList(boards);
    }

    public BoardResponse createBoard(BoardRequest boardRequest){
        User owner = userRepository.findById(boardRequest.getOwner())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + boardRequest.getOwner()));
        Board board = BoardMapper.toEntity(boardRequest);
        board.setOwner(owner);
        board.setCreatedAt(LocalDateTime.now());
        return BoardMapper.toBoardResponse(boardRepository.save(board));
    }

    public BoardResponseDetails getBoardById(Long id){
        Board board = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found with id: "+id));
        return BoardMapper.toBoardResponseDetails(board);
    }

    public BoardResponse updateBoard(Long id, BoardRequest boardRequest){
        Board existingBoard = boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Board not found with id: "+id)
        );
        User existingUser = userRepository.findById(
                boardRequest.getOwner()
        ).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + boardRequest.getOwner()));
        existingBoard.setTitle(boardRequest.getTitle());
        existingBoard.setDescription(boardRequest.getDescription());
        existingBoard.setOwner(existingUser);
        existingBoard.setUpdatedAt(LocalDateTime.now());
        return BoardMapper.toBoardResponse(boardRepository.save(existingBoard));
    }

    public void deleteBoard(Long id){
        Board existingBoard = boardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Board not found with id: "+id)
        );
        boardRepository.delete(existingBoard);
    }

}
