package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookHistoryRepository {

    private final List<Book> bookHistoryList;

    public BookHistoryRepository(List<Book> bookHistoryList) {
        this.bookHistoryList = bookHistoryList;
    }

    public BookHistoryRepository() {
        bookHistoryList = new ArrayList<>();
    }

    public void addBook(Book book) {
        bookHistoryList.add(book);
    }

    public List<Book> getBookHistory() {
        return bookHistoryList;
    }
}
