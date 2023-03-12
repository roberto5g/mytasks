package com.mytasks.app.exceptions;

public class AccessForbiddenException extends RuntimeException{
    public AccessForbiddenException(String text){
        super("You don't have permission to "+text+" this resource.");
    }
}
