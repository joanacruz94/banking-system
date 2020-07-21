package com.ironhack.server.exceptions;

public class NotAuthorizeException extends RuntimeException{
    public NotAuthorizeException(String message){
        super(message);
    }
}
