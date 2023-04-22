package com.mytasks.app;

import com.mytasks.app.model.Comment;
import com.mytasks.app.model.Task;
import com.mytasks.app.model.User;

import java.time.LocalDateTime;

public class TestCommentFactory {
    public static Comment createTestComment(User user, Task task) {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setCreator(user);
        comment.setDescription("comment description");
        comment.setCreatedAt(LocalDateTime.now());
        comment.setTask(task);
        return comment;
    }
}
