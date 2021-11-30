package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerTest {

    BookRepository bookRepository;

    @BeforeEach
    void setup(){
        bookRepository = new BookRepository();
    }

    @Value("${server.port}")
    private int port;

    @Test
    void givenAnExistingRepository_whenGettingAllBooks_thenReceiveHttpStatusOKAndListOfBooks(){

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
}


