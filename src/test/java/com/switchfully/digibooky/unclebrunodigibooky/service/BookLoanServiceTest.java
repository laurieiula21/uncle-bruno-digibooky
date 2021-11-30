package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookLoanServiceTest {
    BookLoanService bookLoanService;


    @Test
    void givenBookLoanAndBookRepository_whenLendingABookByIsbn_thenCheckIfIsbnIsInBookLoanRepository() {
        //Assertions.assertThat(bookLoanService.getAllBookLoans().contains()).isFalse();
        // when
//        bookLoanService.lendBook("isbn1");
//
//        // then
//        boolean foundBookLoan = bookLoanService.getAllBookLoans()
//                .stream()
//                .anyMatch(bookLoan -> bookLoan.getBookId().equals("isbn1"));

        //Assertions.assertThat(bookLoanService.getAllBookLoans().contains()).isTrue();
    }

}