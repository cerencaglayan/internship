package com.example.demo.myproject.exception;

public class UserIsNotActiveException extends Exception{
    public UserIsNotActiveException() {
        super();
    }

    public UserIsNotActiveException(String message) {
        super(message);
    }
}
