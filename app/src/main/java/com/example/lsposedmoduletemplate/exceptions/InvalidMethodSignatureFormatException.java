package com.example.lsposedmoduletemplate.exceptions;

public class InvalidMethodSignatureFormatException extends RuntimeException {
    public InvalidMethodSignatureFormatException(String message) {
        super(message);
    }

    public InvalidMethodSignatureFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}