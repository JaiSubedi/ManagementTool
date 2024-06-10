package com.test.toolmanagement.exception;

public class ToolNotFoundException extends Exception {
    public ToolNotFoundException(String message) {
        super(message);
    }
}