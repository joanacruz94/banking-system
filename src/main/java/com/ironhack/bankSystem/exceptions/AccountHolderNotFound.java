package com.ironhack.bankSystem.exceptions;

public class AccountHolderNotFound extends RuntimeException {
    public AccountHolderNotFound(){
        super();
    }

    public AccountHolderNotFound(String message){
        super(message);
    }
}
