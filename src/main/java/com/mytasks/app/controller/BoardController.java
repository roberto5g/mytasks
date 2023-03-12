package com.mytasks.app.controller;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public List<BoardResponse> getAllBoards(){
        return boardService.getAllBoards();
    }

    @PostMapping
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest){
        return boardService.createBoard(boardRequest);
    }

    @GetMapping("/{id}")
    public BoardResponse getBoardById(@PathVariable Long id){
        return boardService.getBoardById(id);
    }

    @PutMapping("/{id}")
    public BoardResponse updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest){
        return boardService.updateBoard(id, boardRequest);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
