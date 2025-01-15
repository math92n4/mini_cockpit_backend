package com.example.mini_cockpit_backend.exception;

import java.util.List;

public class FileException extends Exception{

    private List<String> errorMessages;

    public FileException(List<String> errorMessages) {
        super("CSV fejl");
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

}
