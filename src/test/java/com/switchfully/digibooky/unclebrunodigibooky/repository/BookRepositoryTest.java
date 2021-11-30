package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookRepositoryTest {

    BookRepository bookRepository;

    @BeforeEach
    void setup(){
        bookRepository = new BookRepository();
    }

    @Test
    void givenABookRepositoryWithThreeBooks_WhenAddingThreeBooksToTheRepository_ThenListSizeShouldBeSix(){
        // Given
        int originalBookListSize = bookRepository.getAllBooks().size();
        bookRepository.addBook(new Book("isbn1", "Title 1", new Author("First", "Last"),"This is the summary of 69" ));
        bookRepository.addBook(new Book("isbn2", "Title 2", new Author("First", "Last"),"This is the summary of 69" ));
        bookRepository.addBook(new Book("isbn3", "Title 3", new Author("First", "Last"),"This is the summary of 69" ));

        // When
        int numberOfBooks = bookRepository.getAllBooks().size();
        // Then
        Assertions.assertThat(numberOfBooks).isEqualTo(originalBookListSize + 3);
    }

}