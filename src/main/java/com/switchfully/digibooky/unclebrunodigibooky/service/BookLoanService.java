package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookService bookService;

    public BookLoanService(BookLoanRepository bookLoanRepository, BookService bookService) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookService = bookService;
    }

    public List<BookLoan> getAllBookLoans() {
        return bookLoanRepository.getBookLoanList();
    }

    public void lendBook(String isbn, String userIdToFind) {
        String bookId = bookService.getOneBook(isbn).getId();
        if (!isBookAvailable(bookId)) {
            throw new BookNotAvailableException("The book with isbn: " + isbn + " is not available anymore.");
        }
        String userId = userIdToFind;
        LocalDate lentOutDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusWeeks(3);
        BookLoan bookLoan = new BookLoan(bookId, userId, lentOutDate, returnDate);
        bookLoanRepository.addBookLoan(bookLoan);
    }

    private boolean isBookAvailable(String bookId) {
        return getAllBookLoans().stream().noneMatch(loan -> loan.getBookId().equals(bookId));
    }
}
