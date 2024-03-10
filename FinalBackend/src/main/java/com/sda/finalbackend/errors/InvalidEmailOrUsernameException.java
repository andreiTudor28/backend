package com.sda.finalbackend.errors;

public class InvalidEmailOrUsernameException extends Exception{

    public InvalidEmailOrUsernameException(String message)
    {
        super(message);
    }
}
