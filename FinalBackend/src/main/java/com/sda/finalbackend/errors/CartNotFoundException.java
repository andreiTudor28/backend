package com.sda.finalbackend.errors;

public class CartNotFoundException extends Exception{

    public CartNotFoundException(String message){
        super(message);
    }
}
