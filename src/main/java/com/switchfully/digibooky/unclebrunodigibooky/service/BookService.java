package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.IsbnDoesNotExistException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book getOneBook(String isbn) {
        //Story 2
        for (Book book : bookRepository.getAllBooks()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        throw new IsbnDoesNotExistException("The isbn does not exist");
    }

    /**
     * Story 3
     * @param isbn - isbn number
     * @return list of books containing (part of) isbn
     */
    public List<Book> searchBookByISBN(String isbn) {

        return bookRepository.getAllBooks().stream()
                .filter(book -> book.getIsbn().contains(isbn))
                .toList();

    }

    /*As a librarian I want to register a new book so I can keep the collections of books relevant.

The ISBN, title and author's last name are required.
If any other user besides a librarian tries to register a new book, let the server respond with 403 Forbidden and a custom message.
Endpoint: (POST) ../domain/books*/

    /**
     * Story 10A
     * @param book
     * adds a book to the list of books
     */
    public void registerBook (Book book) {
        bookRepository.addBook(book);
    }
}
