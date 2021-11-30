package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.CreateBookLoanDto;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookLoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/bookloans")
public class BookLoanController {

    private BookLoanService bookLoanService;

    public BookLoanController(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void lendBook(@RequestBody CreateBookLoanDto createBookLoanDto) {
        bookLoanService.lendBook(createBookLoanDto.getIsbn(), createBookLoanDto.getUserId());
    }

}
