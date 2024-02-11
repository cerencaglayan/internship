package com.example.demo.myproject.exception;

public class UserAlreadyActiveException extends Exception{
    public UserAlreadyActiveException() {
        super();
    }

    public UserAlreadyActiveException(String message) {
        super(message);
    }
}
