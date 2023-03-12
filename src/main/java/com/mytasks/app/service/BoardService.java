package com.mytasks.app.service;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.exceptions.AccessForbiddenException;
import com.mytasks.app.exceptions.BoardNotFoundException;
import com.mytasks.app.exceptions.UserNotFoundException;
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
        User owner = userRepository.findById(boardRequest.getOwner()).orElseThrow(
                () -> new UserNotFoundException(boardRequest.getOwner())
        );
        Board board = BoardMapper.toEntity(boardRequest);
        board.setOwner(owner);
        board.setCreatedAt(LocalDateTime.now());
        return BoardMapper.toBoardResponse(boardRepository.save(board));
    }

    public BoardResponseDetails getBoardById(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        return BoardMapper.toBoardResponseDetails(board);
    }

    public BoardResponse updateBoard(Long id, BoardRequest boardRequest){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new BoardNotFoundException(id)
        );
        User owner = userRepository.findById(boardRequest.getOwner()).orElseThrow(
                () -> new UserNotFoundException(boardRequest.getOwner())
        );
        if (!board.getOwner().equals(owner)){
            throw new AccessForbiddenException("update");
        }
        board.setTitle(boardRequest.getTitle());
        board.setDescription(boardRequest.getDescription());
        board.setOwner(owner);
        board.setUpdatedAt(LocalDateTime.now());
        return BoardMapper.toBoardResponse(boardRepository.save(board));
    }

    public void deleteBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new BoardNotFoundException(boardId)
        );
        Long userId = 1L;
        User owner = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
        if (!board.getOwner().equals(owner)){
            throw new AccessForbiddenException("delete");
        }
        boardRepository.delete(board);
    }

}
