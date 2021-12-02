package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookLoanServiceTest {
    @Autowired
    BookLoanService bookLoanService;
    @Autowired
    BookService bookService;
    @Autowired
    BookLoanRepository bookLoanRepository;

    @Test
    @DisplayName("Testing lendbook method. Check if available book is added to bookloan repository")
    void givenBookLoanAndBookRepository_whenLendingABookByIsbnThatIsAvailable_thenCheckIfIsbnIsAddedToBookLoanRepository() {
        List<Book> booksWithIsbn1 = bookService.searchBookByISBN("isbn1");

        Book bookWithId = booksWithIsbn1.stream()
                .filter(book -> bookLoanService.isBookAvailable(book.getId()))
                .findFirst()
                .orElse(null);
        /**
         * Check if book is not already added to the bookloan list. You can lend the book.
         * */
        Assertions.assertThat(bookWithId).isNotNull();

        // When
        String loanId = bookLoanService.lendBook("isbn1", "userIdToFind");
        bookWithId = booksWithIsbn1.stream()
                .filter(book -> bookLoanService.isBookAvailable(book.getId()))
                .findFirst()
                .orElse(null);
        /**
         * Check if book is added to the bookloan list. The specific book is not available anymore.
         * */
        // Then
        Assertions.assertThat(bookWithId).isNull();
        bookLoanService.returnBook(loanId);
    }

    @Test
    void givenANotAvailableBook_whenTryingToLendThatBook_thenThrowBookNotAvailableException() {
        // Given
        // When
        String loanId = bookLoanService.lendBook("isbn1", "userIdToFind");
        // Then
        assertThatExceptionOfType(BookNotAvailableException.class)
                .isThrownBy(() -> bookLoanService.lendBook("isbn1", "userIdToFind"));
        bookLoanService.returnBook(loanId);
    }

    @Test
    void givenABookIdThatIsInTheLoanRepository_whenTryingToRemoveThatBook_thenBookIsNotInTheRepository() {
        // given
        String bookLoanId = bookLoanService.lendBook("isbn1", "userIdToFind");

        // when
        bookLoanService.returnBook(bookLoanId);

        // then
        BookLoan bookLoan = bookLoanService.getAllBookLoans().stream()
                .filter(loan -> loan.getLoanId().equals(bookLoanId))
                .findFirst()
                .orElse(null);

        Assertions.assertThat(bookLoan).isNull();
    }

    @Test
    void givenABookLoanIdThatIsNotInTheRepository_whenTryingToRemoveThatBook_thenExpectingException() {
        //given
        //when
        // then
        Assertions.assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> bookLoanService.returnBook("wrongId"));
    }

    @Test
    void givenAUserId_whenGettingAllBorrowedBooksForUserId_thenReturnListOfBorrowedBooks() {
        // Given
        String loanIdIsbn1 = bookLoanService.lendBook("isbn1", "userIdToFind");
        String loanIdIsbn2 = bookLoanService.lendBook("isbn2", "userIdToFind");
        String loanIdIsbn3 = bookLoanService.lendBook("isbn3", "userIdToNotFind");
        List<String> bookIdsBorrowedByUser = bookLoanService.getAllBookLoans().stream()
                .filter(book -> book.getUserId().equals("userIdToFind"))
                .map(book -> book.getBookId())
                .collect(Collectors.toList());

        // When
        List<Book> bookListBorrowedByUser = bookLoanService.getBooksBorrowedBy("userIdToFind");
        // Then

        Assertions.assertThat(bookListBorrowedByUser.stream().map(Book::getId)).containsAll(bookIdsBorrowedByUser);
        bookLoanService.returnBook(loanIdIsbn1);
        bookLoanService.returnBook(loanIdIsbn2);
        bookLoanService.returnBook(loanIdIsbn3);
    }

    @Test
    void givenAUserIdWithoutBorrowedBooks_whenGettingAllBorrowedBooksForUserId_thenReturEmptyList() {
        // Given
        List<String> stringList = new ArrayList<>();
        // When
        List<Book> bookListBorrowedByUser = bookLoanService.getBooksBorrowedBy("userIdToFind");
        // Then
        Assertions.assertThat(bookListBorrowedByUser.stream().map(Book::getId)).containsAll(stringList);
    }

    @Test
    void givenTwoLentOutBooksOneLate_whenGettingAllOverdueBooks_thenCompareListOfBooks() {
        // Given
        String loanIdIsbn1 = bookLoanService.lendBook("isbn1", "userIdToFind");
        String loanIdIsbn2 = bookLoanService.lendBook("isbn2", "userIdToFind");
        //For testing setting the date before today
        bookLoanRepository.setReturnDate(loanIdIsbn1, LocalDate.now().minusDays(1));

        List<Book> overdueBooksValid = bookLoanService.getBooksBorrowedBy("userIdToFind").stream()
                .filter(book -> book.getIsbn().equals("isbn1"))
                .collect(Collectors.toList());

        // When
        List<Book> overdueBooks = bookLoanService.getAllOverdueBooks();

        // Then
        Assertions.assertThat(overdueBooks).containsAll(overdueBooksValid);

        bookLoanService.returnBook(loanIdIsbn1);
        bookLoanService.returnBook(loanIdIsbn2);
    }

    @Test
    void givenTwoLentOutBooksTwoBooksLateDifferentUser_whenGettingAllOverdueBooks_thenCompareListOfBooks() {
        // Given
        String loanIdIsbn1 = bookLoanService.lendBook("isbn1", "userIdToFind");
        String loanIdIsbn2 = bookLoanService.lendBook("isbn2", "userIdToFind");
        String loanIdIsbn3 = bookLoanService.lendBook("isbn3", "OtherUserToFInd");
        //For testing setting the date before today
        bookLoanRepository.setReturnDate(loanIdIsbn1, LocalDate.now().minusDays(1));
        bookLoanRepository.setReturnDate(loanIdIsbn3, LocalDate.now().minusDays(1));

        List<Book> overdueBooksValid = bookLoanService.getAllBookLoans().stream()
                .filter(bookLoan -> bookLoan.getLoanId().equals(loanIdIsbn1) || bookLoan.getLoanId().equals(loanIdIsbn3))
                .map(bookLoanService::getBookFromLoan)
                .collect(Collectors.toList());

        // When
        List<Book> overdueBooks = bookLoanService.getAllOverdueBooks();

        // Then
        Assertions.assertThat(overdueBooks).containsAll(overdueBooksValid);

        bookLoanService.returnBook(loanIdIsbn1);
        bookLoanService.returnBook(loanIdIsbn2);
        bookLoanService.returnBook(loanIdIsbn3);
    }

    @Test
    void givenTwoLentOutBooksNoneLate_whenGettingAllOverdueBooks_thenCompareListOfBooks() {
        // Given
        String loanIdIsbn1 = bookLoanService.lendBook("isbn1", "userIdToFind");
        String loanIdIsbn2 = bookLoanService.lendBook("isbn2", "userIdToFind");

        List<Book> overdueBooksValid = new ArrayList<>();

        // When
        List<Book> overdueBooks = bookLoanService.getAllOverdueBooks();

        // Then
        Assertions.assertThat(overdueBooks).containsAll(overdueBooksValid);

        bookLoanService.returnBook(loanIdIsbn1);
        bookLoanService.returnBook(loanIdIsbn2);
    }
}