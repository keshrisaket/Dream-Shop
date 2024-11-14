package com.saket.dreamshop.exception;

public class ProductNotFoundException extends  RuntimeException
{

    public ProductNotFoundException(String message){
        super(message);
    }
}
