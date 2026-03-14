package com.jay.AirBnb.Exceptions;

public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message){
        super(message);
    }
}
