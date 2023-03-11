package com.mytasks.app.exceptions;

public class AccessForbiddenException extends RuntimeException{
    public AccessForbiddenException(){
        super("You don't have permission to edit this resource.");
    }
}
