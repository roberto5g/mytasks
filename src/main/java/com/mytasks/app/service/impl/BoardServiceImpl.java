package com.mytasks.app.service.impl;

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
import com.mytasks.app.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BoardResponse> getAllBoards(){
        List<Board> boards = boardRepository.findAll();
        return BoardMapper.toBoardResponseList(boards);
    }

    @Override
    public BoardResponse createBoard(BoardRequest boardRequest){
        User owner = userRepository.findById(boardRequest.getOwner()).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+boardRequest.getOwner())
        );
        Board board = BoardMapper.toEntity(boardRequest);
        board.setOwner(owner);
        board.setCreatedAt(LocalDateTime.now());
        return BoardMapper.toBoardResponse(boardRepository.save(board));
    }

    @Override
    public BoardResponseDetails getBoardById(Long id){
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
        User owner = userRepository.findById(boardRequest.getOwner()).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+boardRequest.getOwner())
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

    @Override
    public void deleteBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new BoardNotFoundException(boardId)
        );
        Long userId = 1L;
        User owner = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+userId)
        );
        if (!board.getOwner().equals(owner)){
            throw new AccessForbiddenException("delete");
        }
        boardRepository.delete(board);
    }

}
