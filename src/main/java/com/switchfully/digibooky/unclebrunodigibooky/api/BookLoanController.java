package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.CreateBookLoanDto;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookLoanService;
import com.switchfully.digibooky.unclebrunodigibooky.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/bookloans")
public class BookLoanController {

    private final BookLoanService bookLoanService;
    private final UserService userService;

    public BookLoanController(BookLoanService bookLoanService, UserService userService) {
        this.bookLoanService = bookLoanService;
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void lendBook(@RequestBody CreateBookLoanDto createBookLoanDto) {
        userService.getUserById(createBookLoanDto.getUserId());
        bookLoanService.lendBook(createBookLoanDto.getIsbn(), createBookLoanDto.getUserId());
    }


}
