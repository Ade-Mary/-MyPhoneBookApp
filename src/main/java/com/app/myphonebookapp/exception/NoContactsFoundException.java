package com.app.myphonebookapp.exception;

public class NoContactsFoundException extends RuntimeException {
    public NoContactsFoundException(String message) {
        super(message);
    }
}

