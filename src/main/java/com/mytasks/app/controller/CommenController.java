package com.mytasks.app.controller;

import com.mytasks.app.dto.CommentResponse;
import com.mytasks.app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommenController {

    @Autowired
    private CommentService commenService;

    @GetMapping("/task/{taskId}")
    public List<CommentResponse> getCommentsByTask(@PathVariable Long taskId){

        List<CommentResponse> commentDTOS = commenService.getCommentsByTask(taskId);
        return commentDTOS;
    }
}
