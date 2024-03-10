package com.sda.finalbackend.errors;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message)
    {
        super(message);
    }
}
