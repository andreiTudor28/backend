package com.sda.finalbackend.errors;

public class InvalidCredentialsException extends  Exception{

    public InvalidCredentialsException(String message)
    {
        super(message);
    }

}
