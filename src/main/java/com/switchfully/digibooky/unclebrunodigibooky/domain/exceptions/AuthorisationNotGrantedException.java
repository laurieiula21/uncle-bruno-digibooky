package com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions;

public class AuthorisationNotGrantedException extends RuntimeException {

    public AuthorisationNotGrantedException(String message) {
        super(message);
    }
}
