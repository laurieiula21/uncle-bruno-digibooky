package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {
    BookRepository bookRepository;

    @BeforeEach
    void setup(){
        bookRepository = new BookRepository();
    }

    @Test
    void givenAnEmptyBookRepository_WhenGettingAllBooks_ThenReturnAnEmplyList(){
        // Given
        // When
        List<Book> allBooks = bookRepository.getAllBooks();

        // Then
        Assertions.assertThat(allBooks).isEmpty();
    }

}