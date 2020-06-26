package com.ironhack.bankSystem.exceptions;

public class FraudDetectionException extends RuntimeException{
    public FraudDetectionException(){
        super();
    }

    public FraudDetectionException(String message){
        super(message);
    }
}
