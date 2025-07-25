package com.example.cmssystem.exception.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}