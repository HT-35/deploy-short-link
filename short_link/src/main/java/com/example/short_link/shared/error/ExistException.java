package com.example.short_link.shared.error;

public class ExistException extends RuntimeException {


    public ExistException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
