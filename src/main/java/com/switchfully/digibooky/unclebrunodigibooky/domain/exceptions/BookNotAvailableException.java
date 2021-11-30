package com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions;

public class BookNotAvailableException  extends RuntimeException{
    public BookNotAvailableException(String message) {
        super(message);
    }
}
