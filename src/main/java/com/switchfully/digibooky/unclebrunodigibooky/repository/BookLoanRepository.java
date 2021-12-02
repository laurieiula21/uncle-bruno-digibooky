package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
                .orElseThrow(() -> new NoSuchElementException("The bookloan id isn't in the repository."));
        bookLoanList.remove(bookLoan);
        return bookLoan;
    }

    public void setReturnDate(String bookLoanId, LocalDate newReturnDate) {
        bookLoanList.stream()
                .filter(bookLoan -> bookLoan.getLoanId().equals(bookLoanId))
                .forEach(bookLoan -> bookLoan.setReturnDate(newReturnDate));
    }
}
