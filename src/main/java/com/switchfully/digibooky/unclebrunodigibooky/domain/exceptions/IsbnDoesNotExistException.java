package com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions;

public class IsbnDoesNotExistException extends RuntimeException {

    public IsbnDoesNotExistException(String message) {
        super(message);
    }
}
