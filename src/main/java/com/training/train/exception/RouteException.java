package com.training.train.exception;

public class RouteException extends Exception{

    private final String message;

    public RouteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
