package com.mytasks.app.service;

import com.mytasks.app.dto.CommentDTO;
import com.mytasks.app.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommenService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> getCommentsByTask(Long id){
        return null;
    }
}
