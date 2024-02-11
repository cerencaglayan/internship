package com.example.demo.myproject.exception;

public class TokenExpiredException extends Exception {

    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
