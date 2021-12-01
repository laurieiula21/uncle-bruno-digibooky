package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.CreateBookLoanDto;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookLoanService;
import com.switchfully.digibooky.unclebrunodigibooky.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bookloans")
public class BookLoanController {

    private final BookLoanService bookLoanService;
    private final UserService userService;
    private final Logger myLogger = LoggerFactory.getLogger(BookLoanController.class);


    public BookLoanController(BookLoanService bookLoanService, UserService userService) {
        this.bookLoanService = bookLoanService;
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void lendBook(@RequestBody CreateBookLoanDto createBookLoanDto) {
        myLogger.info("Loan: isbn:" + createBookLoanDto.getIsbn() + " by user: " + createBookLoanDto.getUserId() + " has been queried");
        userService.getUserById(createBookLoanDto.getUserId());
        myLogger.info("Loan user: " + createBookLoanDto.getUserId() + " has been validated.");
        bookLoanService.lendBook(createBookLoanDto.getIsbn(), createBookLoanDto.getUserId());
        myLogger.info("Created loan: isbn:" + createBookLoanDto.getIsbn() + " by user: " + createBookLoanDto.getUserId());
    }

    @PutMapping(path = "/return/{bookloanId}", produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public String returnBook(@PathVariable("bookloanId") String bookloanId) {
        myLogger.info("Returning bookId: " + bookloanId + " .");
        return bookLoanService.returnBook(bookloanId);
    }


}
