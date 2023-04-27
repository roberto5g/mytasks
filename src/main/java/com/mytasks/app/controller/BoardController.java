package com.mytasks.app.controller;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<BoardResponse> getAllBoards(){
        return boardService.getAllBoards();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/owner")
    public List<BoardResponse> getBoardsByOwner(){
        return boardService.getAllBoardsByOwner();
    }

    @PostMapping
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest){
        return boardService.createBoard(boardRequest);
    }

    @GetMapping("/{id}")
    public BoardResponse getBoardById(@PathVariable Long id){
        return boardService.getBoardDetailsById(id);
    }

    @PutMapping("/{id}")
    public BoardResponse updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest){
        return boardService.updateBoard(id, boardRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
