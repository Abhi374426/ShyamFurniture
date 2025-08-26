package com.shyamfurniture.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(){
        super();
    }
    public BadRequestException(String str){
        super(str);
    }
}
