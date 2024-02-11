package com.example.demo.myproject.exception;

public class PasswordNotValidException extends Exception{
    public PasswordNotValidException() {
        super();
    }

    public PasswordNotValidException(String message) {
        super(message);
    }
}
