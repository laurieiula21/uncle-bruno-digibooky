package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.IsbnDoesNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IsbnDoesNotExistException.class)
    protected void isbnDoesNotExist(IsbnDoesNotExistException ex,
                                           HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }
}
