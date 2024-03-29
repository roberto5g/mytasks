package com.mytasks.app.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessForbiddenException.class)
    public ResponseEntity<ErrorObject> handleEntityNotFoundException(AccessForbiddenException ex, WebRequest request) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(errorObject, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorObject> handleBoardNotFoundException(BoardNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorObject> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorObject> handleCommentNotFoundException(CommentNotFoundException ex, WebRequest request) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorObject> handleUserCreateException(UserException ex, WebRequest request) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<ErrorObject> handleUserUpdateException(UserUpdateException ex, WebRequest request) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorObject> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorObject errorObject = getErrorObject(ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }

    private static ErrorObject getErrorObject(String message, int statusCode) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(statusCode);
        errorObject.setMessage(message);
        errorObject.setTimestamp(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss", Locale.ENGLISH)
                .format(LocalDateTime.now()));
        return errorObject;
    }

}
