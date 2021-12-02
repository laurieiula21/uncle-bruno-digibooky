package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.BookMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.CreateBookLoanDto;
import com.switchfully.digibooky.unclebrunodigibooky.service.AuthorisationService;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookLoanService;
import com.switchfully.digibooky.unclebrunodigibooky.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/bookloans")
public class BookLoanController {

    private final BookLoanService bookLoanService;
    private final UserService userService;
    private final AuthorisationService authorisationService;
    private final BookMapper bookMapper;
    private final Logger myLogger = LoggerFactory.getLogger(BookLoanController.class);


    public BookLoanController(BookLoanService bookLoanService, UserService userService, AuthorisationService authorisationService, BookMapper bookMapper) {
        this.bookLoanService = bookLoanService;
        this.userService = userService;
        this.authorisationService = authorisationService;
        this.bookMapper = bookMapper;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void lendBook(@RequestBody CreateBookLoanDto createBookLoanDto, @RequestHeader(required = false) String authorization) {
        myLogger.info("Loan: isbn:" + createBookLoanDto.getIsbn() + " by user: " + createBookLoanDto.getUserId() + " has been queried");
        userService.getUserById(createBookLoanDto.getUserId());
        authorisationService.validateAuthorisation(DigibookyFeature.LEND_BOOK, authorization);
        bookLoanService.lendBook(createBookLoanDto.getIsbn(), createBookLoanDto.getUserId());
        myLogger.info("Created loan: isbn:" + createBookLoanDto.getIsbn() + " by user: " + createBookLoanDto.getUserId());
    }

    @PutMapping(path = "/return/{bookloanId}", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String returnBook(@PathVariable("bookloanId") String bookloanId, @RequestHeader(required = false) String authorization) {
        myLogger.info("Returning bookId: " + bookloanId + " .");
        authorisationService.validateAuthorisation(DigibookyFeature.RETURN_BOOK, authorization);
        String message = bookLoanService.returnBook(bookloanId);
        myLogger.info("Returning book successful.");
        return message;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBorrowedBooksBy(@RequestParam String userId, @RequestHeader(required = false) String authorization) {
        myLogger.info("Borrowed books by user with id: " + userId + " .");
        authorisationService.validateAuthorisation(DigibookyFeature.GET_ALL_BORROWED_BOOKS_OF_USER, authorization);
        List<Book> booksBorrowedByUser = bookLoanService.getBooksBorrowedBy(userId);
        List<BookDto> bookDtoList = booksBorrowedByUser.stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
        myLogger.info("Returning borrowed Book Dtos of user successful.");
        return bookDtoList;
    }

    @GetMapping(path = "/overdueBooks", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllOverdueBooks(@RequestHeader(required = false) String authorization) {
        myLogger.info("Get all overdue books");
        authorisationService.validateAuthorisation(DigibookyFeature.GET_ALL_OVERDUE_BOOKS, authorization);
        List<Book> overdueBooks = bookLoanService.getAllOverdueBooks();
        List<BookDto> bookDtoList = overdueBooks.stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
        myLogger.info("Getting all overdue books successful.");
        return bookDtoList;
    }

}
