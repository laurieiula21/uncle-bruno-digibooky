package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.IsbnDoesNotExistException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


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

    public List<Book> searchBookByISBN(String isbn) {

        return bookRepository.getAllBooks().stream()
                .filter(book -> book.getIsbn().contains(isbn))
                .toList();
    }

    public void registerBook(Book book) {
        bookRepository.addBook(book);
    }


    public Collection<Book> searchBooksByTitle(String title) {
        return bookRepository.getAllBooks().stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList());
    }
}
