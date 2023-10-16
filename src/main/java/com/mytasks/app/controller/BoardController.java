package com.mytasks.app.controller;

import com.mytasks.app.dto.BoardRequest;
import com.mytasks.app.dto.BoardResponse;
import com.mytasks.app.dto.BoardResponseDetails;
import com.mytasks.app.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Get all boards")
    public List<BoardResponse> getAllBoards(){
        return boardService.getAllBoards();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/owner")
    @Operation(summary = "Get all boards by owner or all boards to admin")
    public List<BoardResponse> getBoardsByOwner(){
        return boardService.getAllBoardsByOwner();
    }

    @PostMapping
    @Operation(summary = "Create a new board")
    public BoardResponse createBoard(@RequestBody BoardRequest boardRequest){
        return boardService.createBoard(boardRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get board by id")
    public BoardResponseDetails getBoardById(@PathVariable Long id){
        return boardService.getBoardDetailsById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update board")
    public BoardResponse updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest){
        return boardService.updateBoard(id, boardRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete board by id")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id){
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
