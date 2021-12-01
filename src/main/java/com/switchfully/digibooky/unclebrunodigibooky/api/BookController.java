package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.BookMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import com.switchfully.digibooky.unclebrunodigibooky.service.AuthorisationService;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;
    private final AuthorisationService authorisationService;
    private final BookMapper bookMapper;
    private final Logger myLogger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, AuthorisationService authorisationService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.authorisationService = authorisationService;
        this.bookMapper = bookMapper;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        List<Book> bookList = bookService.getAllBooks();
        List<BookDto> bookDtoList = bookList.stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
        return bookDtoList;
    }

    @GetMapping(path = "/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable("isbn") String isbn) {
        Book book = bookService.getOneBook(isbn);
        BookDto bookDto = bookMapper.mapBookToDto(book);
        return bookDto;
    }

    /**
     * Story 3
     *
     * @param isbn
     * @return list of books containing (part of) isbn
     */
    @GetMapping(produces = "application/json", params = "isbn")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> search(@RequestParam String isbn) {
        myLogger.info(isbn + " has been queried");
        return bookService.searchBookByISBN(isbn).stream()
                .map(bookMapper::mapBookToDto)
                .toList();
    }

    /**
     * @param bookDto
     * @param authorization return : adds a book to the list of books
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewBook(@RequestBody BookDto bookDto, @RequestHeader(required = false) String authorization) {
        myLogger.info("RegisterNewBook Method called");
        authorisationService.validateAuthorisation(DigibookyFeature.REGISTER_NEW_BOOK, authorization);
        Book book = bookMapper.mapBookDtoToBook(bookDto);
        bookService.registerBook(book);
        myLogger.info("RegisterNewBook Method successfully ended");
    }
}
