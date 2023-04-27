package com.mytasks.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponse {
    private Long id;
    private String title;
    private String description;
    private UserResponseTask owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
