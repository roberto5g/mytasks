package com.mytasks.app.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private String timestamp;
}
