package com.mytasks.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private String description;
    private Long taskId;
}
