package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookLoanServiceTest {

    @Autowired
    BookLoanService bookLoanService;
    @Autowired
    BookService bookService;

    @Test
    void givenBookLoanAndBookRepository_whenLendingABookByIsbn_thenCheckIfIsbnIsInBookLoanRepository() {
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

}