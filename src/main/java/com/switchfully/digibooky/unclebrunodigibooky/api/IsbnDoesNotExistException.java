package com.switchfully.digibooky.unclebrunodigibooky.api;

public class IsbnDoesNotExistException extends RuntimeException {

    public IsbnDoesNotExistException(String message) {
        super(message);
    }
}
