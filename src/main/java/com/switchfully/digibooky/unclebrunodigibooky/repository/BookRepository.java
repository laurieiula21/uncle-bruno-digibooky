package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    private final List<Book> bookList;

    public BookRepository() {
        bookList = new ArrayList<>();
    }

    public BookRepository(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getAllBooks() {
        return bookList;
    }
}
