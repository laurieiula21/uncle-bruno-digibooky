package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.AuthorisationNotGrantedException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.InvalidUserException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.IsbnDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger myLogger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(IsbnDoesNotExistException.class)
    protected void isbnDoesNotExist(IsbnDoesNotExistException ex,
                                    HttpServletResponse response) throws IOException {
        myLogger.error("Isbn does not exist" + ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(BookNotAvailableException.class)
    protected void bookIsNotAvailable(BookNotAvailableException ex,
                                      HttpServletResponse response) throws IOException {
        myLogger.error("Book is not available " + ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(InvalidUserException.class)
    protected void invalidUser(InvalidUserException ex,
                               HttpServletResponse response) throws IOException {
        myLogger.error("Invalid user " + ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected void noSuchElement(NoSuchElementException ex,
                                 HttpServletResponse response) throws IOException {
        myLogger.error("No such element found " + ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(AuthorisationNotGrantedException.class)
    protected void authorizationError(AuthorisationNotGrantedException ex,
                                      HttpServletResponse response) throws IOException {
        myLogger.error("authorization not granted " + ex.getMessage());
        response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

}
