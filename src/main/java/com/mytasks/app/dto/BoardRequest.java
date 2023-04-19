package com.mytasks.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {
    private String title;
    private String description;
}
