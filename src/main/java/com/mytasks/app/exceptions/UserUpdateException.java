package com.mytasks.app.exceptions;

public class UserUpdateException extends RuntimeException{
    public UserUpdateException(String text){
        super("Unable to update user. "+text);
    }
}
