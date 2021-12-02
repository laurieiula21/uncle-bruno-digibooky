package com.switchfully.digibooky.unclebrunodigibooky.repository;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Author;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class BookRepository {

    private final List<Book> bookList;

    public BookRepository() {
        bookList = new ArrayList<>();
        populateBooklist();
    }

    // temporarily populating the list
    private void populateBooklist() {
        addBook(new Book("isbn1", "Title 1", new Author("First", "Last"), "This is the summary of 69"));
        addBook(new Book("isbn2", "Title 2", new Author("Second", "Last"), "This is the summary of 69"));
        addBook(new Book("isbn3", "Title 3", new Author("Third", "Last"), "This is the summary of 69"));
        addBook(new Book("isbn4", "Title 4", new Author("First", "Last"), "This is the summary of 69"));
    }

    public BookRepository(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book addBook(Book book) {
        bookList.removeIf(repoBook -> repoBook.getIsbn().equals(book.getIsbn()));
        bookList.add(book);
        return book;
    }

    public void deleteBook(String bookId) {
        Book bookToFind = getAllBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Book with ID: " + bookId + " not found, so it can't be deleted!"));
        bookList.remove(bookToFind);
    }
}
