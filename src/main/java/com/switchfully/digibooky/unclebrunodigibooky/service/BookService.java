package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.IsbnDoesNotExistException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;


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
     *
     * @param isbn - isbn number
     * @return list of books containing (part of) isbn
     */
    public List<Book> searchBookByISBN(String isbn) {

        return bookRepository.getAllBooks().stream()
                .filter(book -> book.getIsbn().contains(isbn))
                .toList();

    }

    public Book getBookBy(String bookId) {
        return bookRepository.getAllBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No book with id: " + bookId + " found"));
    }

    public void registerBook(Book book) {
        bookRepository.addBook(book);
    }


    public Collection<Book> searchBooksByTitle(String title) {

        return bookRepository.getAllBooks().stream()
                .filter(book -> book.getTitle().matches(title))
                .collect(Collectors.toList());
    }

    public List<Book> searchBookByAuthor(String authorName) {

        String query = authorName.replaceAll("\\*", "\\.\\*").replaceAll("\\?", "\\.\\?");

        List<Book> booksByAuthorFirstName = bookRepository.getAllBooks().stream()
                .filter(book -> book.getAuthor().getFirstName().matches(query))
                .toList();

        List<Book> booksByAuthorLastName = (bookRepository.getAllBooks().stream()
                .filter(book -> book.getAuthor().getLastName().matches(query))
                .toList());

        List<Book> booksByAuthorName = new ArrayList<>();
        booksByAuthorName.addAll(booksByAuthorFirstName);
        booksByAuthorName.addAll(booksByAuthorLastName);

        return booksByAuthorName;
    }

}
