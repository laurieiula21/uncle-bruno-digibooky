package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookLoanRepository {
    private final List<BookLoan> bookLoanList;

    public BookLoanRepository() {
        this.bookLoanList = new ArrayList<>();
    }

    public void addBookLoan(BookLoan bookLoan) {
        bookLoanList.add(bookLoan);
    }

    public List<BookLoan> getBookLoanList() {
        return bookLoanList;
    }


    public BookLoan removeBookLoanBy(String bookLoanId) {
        BookLoan bookLoan = bookLoanList.stream()
                .filter(bookloan -> bookloan.getLoanId().equals(bookLoanId))
                .findFirst()
                .orElseGet(null);
        bookLoanList.remove(bookLoan);
        return bookLoan;
    }
}
