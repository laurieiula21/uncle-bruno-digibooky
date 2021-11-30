package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.api.IsbnDoesNotExistException;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


class BookServiceTest {
    BookService bookService;
    BookRepository bookRepository;

    @BeforeEach
    void  beforeEach(){
        this.bookRepository=new BookRepository();
        this.bookService=new BookService(bookRepository);
    }

    @Test
    void GivenANotExistingISBN_WhenGettingOneBook_ThenThrowAnException() {
        assertThatExceptionOfType(IsbnDoesNotExistException.class).isThrownBy(() -> bookService.getOneBook("wrongISBN)"));


    }
}