package com.mytasks.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomAuthenticationException extends InsufficientAuthenticationException {
    public CustomAuthenticationException(String message) {
        super(message);
    }
}

