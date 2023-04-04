package com.mytasks.app.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private LocalDateTime timestamp;
}
