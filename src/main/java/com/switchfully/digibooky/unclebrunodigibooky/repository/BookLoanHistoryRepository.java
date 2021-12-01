package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookLoanHistoryRepository {
    private final List<BookLoan> bookLoanList;

    public BookLoanHistoryRepository(List<BookLoan> bookLoanList) {
        this.bookLoanList = bookLoanList;
    }

    public void addBookLoan(BookLoan bookLoan) {
        bookLoanList.add(bookLoan);
    }

    public List<BookLoan> getBookLoanList() {
        return bookLoanList;
    }
}
