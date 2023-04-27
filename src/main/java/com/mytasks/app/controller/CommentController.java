package com.mytasks.app.controller;

import com.mytasks.app.dto.CommentRequest;
import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/task/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsByTask(@PathVariable Long id){
        List<CommentResponse> comments = commentService.getCommentsByTask(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest){
        CommentResponse commentResponse = commentService.createComment(commentRequest);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        CommentResponse updateComment = commentService.updateComment(id, commentRequest);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
