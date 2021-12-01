package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookLoanServiceTest {
    @Autowired
    BookLoanService bookLoanService;
    @Autowired
    BookService bookService;

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
        bookLoanService.lendBook("isbn1", "userIdToFind");
        bookWithId = booksWithIsbn1.stream()
                .filter(book -> bookLoanService.isBookAvailable(book.getId()))
                .findFirst()
                .orElse(null);
        /**
         * Check if book is added to the bookloan list. The specific book is not available anymore.
         * */
        // Then
        Assertions.assertThat(bookWithId).isNull();
    }

    @Test
    void givenANotAvailableBook_whenTryingToLendThatBook_thenThrowBookNotAvailableException() {
        // Given
        // When
        bookLoanService.lendBook("isbn2", "userIdToFind");
        // Then
        assertThatExceptionOfType(BookNotAvailableException.class)
                .isThrownBy(() -> bookLoanService.lendBook("isbn2", "userIdToFind"));
    }

    @Test
    void givenABookIdThatIsInTheLoanRepository_whenTryingToRemoveThatBook_thenBookIsNotInTheRepository() {
        // given
        bookLoanService.lendBook("isbn3", "userIdToFind");
        List<Book> booksWithIsbn1 = bookService.searchBookByISBN("isbn3");
        Book bookWithId = booksWithIsbn1.stream()
                .filter(book -> !bookLoanService.isBookAvailable(book.getId()))
                .findFirst()
                .orElse(null);

        // when
        bookLoanService.returnBook(bookWithId.getId());

        // then
        bookWithId = booksWithIsbn1.stream()
                .filter(book -> !bookLoanService.isBookAvailable(book.getId()))
                .findFirst()
                .orElse(null);
        Assertions.assertThat(bookWithId).isNull();
    }


}