package com.mytasks.app.exceptions;

public class UserException extends RuntimeException{
    public UserException(String text){
        super(text);
    }
}
