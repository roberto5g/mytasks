package com.mytasks.app.exceptions;

public class EntityNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 1L;
    public EntityNotFoundException(String message){
        super(message);
    }
}
