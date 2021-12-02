package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.BookMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanHistoryRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookLoanService {

    public static final int WEEKS_UNTIL_DUE = 3;
    private final BookLoanRepository bookLoanRepository;
    private final BookLoanHistoryRepository bookLoanHistoryRepository;
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;

    public BookLoanService(BookLoanRepository bookLoanRepository, BookLoanHistoryRepository bookLoanHistoryRepository, BookService bookService, BookMapper bookMapper, UserRepository userRepository) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookLoanHistoryRepository = bookLoanHistoryRepository;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.userRepository= userRepository;
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
        LocalDate returnDate = LocalDate.now().plusWeeks(WEEKS_UNTIL_DUE);
        BookLoan bookLoan = new BookLoan(bookId, userId, lentOutDate, returnDate);
        bookLoanRepository.addBookLoan(bookLoan);
        return bookLoan.getLoanId();
    }

    public boolean isBookAvailable(String bookId) {
        return getAllBookLoans().stream().noneMatch(loan -> loan.getBookId().equals(bookId));
    }

    public String returnBook(String bookLoanId) {
        BookLoan bookLoan = bookLoanRepository.removeBookLoanBy(bookLoanId);
        bookLoanHistoryRepository.addBookLoan(bookLoan);
        if (LocalDate.now().isAfter(bookLoan.getReturnDate())) {
            //Add late fine
            return "Book too late";
        }
        // check for damage fine
        return bookLoan.getLoanId();
    }

    public List<Book> getBooksBorrowedBy(String userId) {
        List<Book> borrowedBooksByUser = bookLoanRepository.getBookLoanList().stream()
                .filter(book -> book.getUserId().equals(userId))
                .map(this::getBookFromLoan)
                .collect(Collectors.toList());
        return borrowedBooksByUser;
    }

    public List<Book> getAllOverdueBooks() {
        List<Book> allOverdueBooks = bookLoanRepository.getBookLoanList().stream()
                .filter(this::isBookLate)
                .map(this::getBookFromLoan)
                .collect(Collectors.toList());
        return allOverdueBooks;
    }

    private boolean isBookLate(BookLoan bookLoan) {
        return LocalDate.now().isAfter(bookLoan.getReturnDate());
    }

    public Book getBookFromLoan(BookLoan bookLoan) {
        Book foundBook = bookService.getBookBy(bookLoan.getBookId());
        return foundBook;
    }

    public String getLenderUser (String userId) {
        for (User user : userRepository.getUserList()) {
            if (user.getId().equals(userId)) {
                return user.getFullName();
            }
        }
        return null;
    }

    public String getBookDetails(Book book) {
        for (BookLoan bookInLoanRepository : bookLoanRepository.getBookLoanList()) {
            if (book.getId().equals(bookInLoanRepository.getBookId())) {
                return "This book is borrowed by " + getLenderUser(bookInLoanRepository.getUserId());
            }
        }
        return "This book is available";
    }
}
