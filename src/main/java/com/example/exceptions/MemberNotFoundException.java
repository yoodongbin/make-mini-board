package com.example.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message) {
        super(message);
    }
}
