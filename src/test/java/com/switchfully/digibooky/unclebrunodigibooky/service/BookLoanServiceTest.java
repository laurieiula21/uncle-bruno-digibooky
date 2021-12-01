package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BookLoanServiceTest {

    BookLoanService bookLoanService;
    BookService bookService;

    @Test
    void givenBookLoanAndBookRepository_whenLendingABookByIsbn_thenCheckIfIsbnIsInBookLoanRepository() {
        List<Book> booksWithIsbn1 = bookService.searchBookByISBN("isbn1");

        Book bookWithId = booksWithIsbn1.stream()
                .filter(book -> bookLoanService.isBookAvailable(book.getId()))
                .findFirst()
                .orElse(null);

        Assertions.assertThat(bookWithId).isNull();

        // When
        bookLoanService.lendBook("isbn1", "userIdToFind");
        bookWithId = booksWithIsbn1.stream()
                .filter(book -> bookLoanService.isBookAvailable(book.getId()))
                .findFirst()
                .orElse(null);

        // Then
        Assertions.assertThat(bookWithId).isNotNull();
    }

}