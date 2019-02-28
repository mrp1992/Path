package com.training.train.exception;

public class TownException extends Exception {

    private String message;

    public TownException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
