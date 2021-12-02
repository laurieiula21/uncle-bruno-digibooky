package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
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

    public void deleteBook(String bookId) {
        if (bookHistoryList.stream().anyMatch(book -> book.getId().equals(bookId))){
            bookHistoryList.removeIf(book -> book.getId().equals(bookId));
        } else {
            throw new BookNotAvailableException("Book is not available. We can't delete it from the history");
        }
    }
}
