package com.ironhack.bankSystem.exceptions;

public class InexistingAccountException extends RuntimeException{
    public InexistingAccountException(){
        super();
    }

    public  InexistingAccountException(String message){
        super(message);
    }
}
