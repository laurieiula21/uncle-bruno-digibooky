package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.BookMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerTest {

    BookRepository bookRepository;
    BookService bookService;

    @BeforeEach
    void setup() {
        bookRepository = new BookRepository();
        bookService = new BookService(bookRepository);
    }

    @Value("${server.port}")
    private int port;

    @Test
    void givenAnExistingRepository_whenGettingAllBooks_thenReceiveHttpStatusOKAndListOfBooks() {

        List<BookDto> bookDtoList = new ArrayList<>();
        List<BookDto> bookList =
                RestAssured
                        .given()
                        .when()
                        .port(port)
                        .get("/books")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(bookDtoList.getClass());
        System.out.println(bookList);

        assertThat(bookRepository.getAllBooks().size()).isEqualTo(bookList.size());
    }

    @Test
    void GivenAnISBN_WhenGettingOneBook_ThenReceiveHttpStatusOKAndSaidSpecificBook() {
        Book book = new Book("isbn1", "Title 1", new Author("First", "Last"), "This is the summary of 69");
        BookDto expectedBookDto = new BookMapper().mapBookToDto(book);
        BookDto actualBookDto =
                RestAssured
                        .given()
                        .when()
                        .port(port)
                        .get("/books/isbn1")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(expectedBookDto.getClass());

        assertThat(actualBookDto.getIsbn()).isEqualTo(expectedBookDto.getIsbn());
        assertThat(actualBookDto.getTitle()).isEqualTo(expectedBookDto.getTitle());
        assertThat(actualBookDto.getAuthor().getFirstName()).isEqualTo(expectedBookDto.getAuthor().getFirstName());
        assertThat(actualBookDto.getAuthor().getLastName()).isEqualTo(expectedBookDto.getAuthor().getLastName());
        assertThat(actualBookDto.getSummary()).isEqualTo(expectedBookDto.getSummary());

    }

    @Test
    void GivenANotExistingISBN_WhenGettingOneBook_ThenThrowAnException() {
        assertThatExceptionOfType(IsbnDoesNotExistException.class).isThrownBy(() -> bookService.getOneBook("wrongISBN)"));
    }
}


