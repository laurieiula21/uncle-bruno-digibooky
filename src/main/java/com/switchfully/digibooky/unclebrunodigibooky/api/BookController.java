package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.service.AuthorisationService;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookService bookService;
    private final AuthorisationService authorisationService;


    public BookController(BookService bookService, AuthorisationService authorisationService) {
        this.bookService = bookService;
        this.authorisationService = authorisationService;
    }


}
