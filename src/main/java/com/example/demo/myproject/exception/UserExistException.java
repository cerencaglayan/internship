package com.example.demo.myproject.exception;

public class UserExistException extends Exception{
    public UserExistException() {
        super();
    }

    public UserExistException(String message) {
        super(message);
    }
}
