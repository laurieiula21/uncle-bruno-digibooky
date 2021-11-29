package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Book;
import com.switchfully.digibooky.unclebrunodigibooky.service.AuthorisationService;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;
    private final AuthorisationService authorisationService;

    @Autowired
    public BookController(BookService bookService, AuthorisationService authorisationService) {
        this.bookService = bookService;
        this.authorisationService = authorisationService;
    }

    @GetMapping(path="/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Book getBook(@PathVariable("isbn") String isbn) {
        return bookService.showDetailsOfBook(isbn);
    }


}
