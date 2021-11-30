package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.api.BookController;
import com.switchfully.digibooky.unclebrunodigibooky.domain.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.Book;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookService;
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

//    @Test
//    void givenAnEmptyBookRepository_WhenGettingAllBooks_ThenReturnAnEmplyList(){
//        // Given
//        // When
//        List<Book> allBooks = bookRepository.getAllBooks();
//
//        // Then
//        Assertions.assertThat(allBooks).isEmpty();
//    }
//
//    @Test
//    void givenABookRepositoryWithThreeBooks_WhenGettingRepository_ThenListSizeShouldBeThree(){
//        // Given
//        bookRepository.addBook(new Book("isbn1", "Title 1", new Author("First", "Last"),"This is the summary of 69" ));
//        bookRepository.addBook(new Book("isbn2", "Title 2", new Author("First", "Last"),"This is the summary of 69" ));
//        bookRepository.addBook(new Book("isbn3", "Title 3", new Author("First", "Last"),"This is the summary of 69" ));
//
//        // When
//        int numberOfBooks = bookRepository.getAllBooks().size();
//        // Then
//        Assertions.assertThat(numberOfBooks).isEqualTo(3);
//    }

}