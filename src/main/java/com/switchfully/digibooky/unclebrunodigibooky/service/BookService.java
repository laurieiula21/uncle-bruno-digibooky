package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.BookNotAvailableException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.exceptions.IsbnDoesNotExistException;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookHistoryRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookLoanRepository;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookLoanRepository bookLoanRepository;
    private final BookHistoryRepository bookHistoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BookLoanRepository bookLoanRepository, BookHistoryRepository bookHistoryRepository) {
        this.bookRepository = bookRepository;
        this.bookLoanRepository = bookLoanRepository;
        this.bookHistoryRepository = bookHistoryRepository;
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

        String query = title.replaceAll("\\*", "\\.\\*").replaceAll("\\?", "\\.\\?");

        List<Book> booksByTitle = bookRepository.getAllBooks().stream()
                .filter(book -> book.getTitle().matches(query))
                .collect(Collectors.toList());

        return booksByTitle;
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

    public Book updateBook(String isbn, Book bookToUpdate) {
        Book updatingBook = getOneBook(isbn);
        if (bookToUpdate.getTitle() != null) {
            updatingBook.setTitle(bookToUpdate.getTitle());
        }
        if (bookToUpdate.getSummary() != null) {
            updatingBook.setSummary(bookToUpdate.getSummary());
        }
        updatingBook.updateAuthor(bookToUpdate.getAuthor().getFirstName(), bookToUpdate.getAuthor().getLastName());

        return bookRepository.addBook(updatingBook);

    }

    public void deleteBookBy(String bookId) {
        if (isBookAvailable(bookId)){
            bookHistoryRepository.addBook(getBookBy(bookId));
            bookRepository.deleteBook(bookId);
        } else {
            throw new BookNotAvailableException("Book is lent out! Not possible to delete it");
        }
    }

    public void restoreBookBy(String bookId){
        if (isBookDeleted(bookId)){
            bookRepository.addBook(getBookBy(bookId));
            bookHistoryRepository.deleteBook(bookId);
        }
    }

    public boolean isBookAvailable(String bookId) {
        List<BookLoan> bookLoanList = bookLoanRepository.getBookLoanList();
        return bookLoanRepository.getBookLoanList().stream()
                .noneMatch(loan -> loan.getBookId().equals(bookId));
    }

    public boolean isBookDeleted(String bookId){
        return bookHistoryRepository.getBookHistory().stream()
                .anyMatch(book -> book.getId().equals(bookId));
    }

    public List<Book> getBookHistory(){
        return bookHistoryRepository.getBookHistory();
    }
}
