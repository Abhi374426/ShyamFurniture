package com.shyamfurniture.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
     public ResourceAlreadyExistsException(String msg){
         super(msg);
     }
    public ResourceAlreadyExistsException(){
        super();
    }
}
