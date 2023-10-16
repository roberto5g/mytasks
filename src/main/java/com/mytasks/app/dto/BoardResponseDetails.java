package com.mytasks.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDetails {
    private Long id;
    private String title;
    private String description;
    private UserResponseTask owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TaskResponse> tasks;
}
