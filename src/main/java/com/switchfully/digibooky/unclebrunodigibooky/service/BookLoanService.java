package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanHistoryRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookLoanHistoryRepository bookLoanHistoryRepository;
    private final BookService bookService;

    public BookLoanService(BookLoanRepository bookLoanRepository, BookLoanHistoryRepository bookLoanHistoryRepository, BookService bookService) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookLoanHistoryRepository = bookLoanHistoryRepository;
        this.bookService = bookService;
    }

    public List<BookLoan> getAllBookLoans() {
        return bookLoanRepository.getBookLoanList();
    }

    public String lendBook(String isbn, String validUserId) {
        String bookId = bookService.getOneBook(isbn).getId();
        if (!isBookAvailable(bookId)) {
            throw new BookNotAvailableException("The book with isbn: " + isbn + " is not available anymore.");
        }
        String userId = validUserId;
        LocalDate lentOutDate = LocalDate.now();
        LocalDate returnDate = LocalDate.now().plusWeeks(3);
        BookLoan bookLoan = new BookLoan(bookId, userId, lentOutDate, returnDate);
        bookLoanRepository.addBookLoan(bookLoan);
        return bookLoan.getLoanId();
    }

    public boolean isBookAvailable(String bookId) {
        return getAllBookLoans().stream().noneMatch(loan -> loan.getBookId().equals(bookId));
    }

    public String returnBook(String bookLoanId) {
        BookLoan bookLoan = bookLoanRepository.removeBookLoanBy(bookLoanId);
        if (bookLoan.getReturnDate().isAfter(LocalDate.now())) {
            //Add late fine
            return "Book too late";
        }
        // check for damage fine
        bookLoanHistoryRepository.addBookLoan(bookLoan);
        return bookLoan.getLoanId();
    }
}
