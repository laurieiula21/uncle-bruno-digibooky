package com.switchfully.digibooky.unclebrunodigibooky.api;

import com.switchfully.digibooky.unclebrunodigibooky.domain.DigibookyFeature;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.api.mapper.BookMapper;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.EnhancedBookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.UserRole;
import com.switchfully.digibooky.unclebrunodigibooky.service.AuthorisationService;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookLoanService;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private final BookService bookService;
    private final AuthorisationService authorisationService;
    private final BookMapper bookMapper;
    private final Logger myLogger = LoggerFactory.getLogger(BookController.class);
    private final BookLoanService bookLoanService;

    @Autowired
    public BookController(BookService bookService, AuthorisationService authorisationService, BookMapper bookMapper, BookLoanService bookLoanService) {
        this.bookService = bookService;
        this.authorisationService = authorisationService;
        this.bookMapper = bookMapper;
        this.bookLoanService = bookLoanService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks(@RequestHeader(required = false) String authorization) {
        myLogger.info("get all books method has been queried");
        authorisationService.getAuthorisationLevel(DigibookyFeature.GET_ALL_BOOKS, authorization);
        List<Book> bookList = bookService.getAllBooks();
        List<BookDto> bookDtoList = bookList.stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
        myLogger.info("get all books method has been successfully finished");
        return bookDtoList;
    }

    @GetMapping(path = "/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public EnhancedBookDto getBook(@PathVariable("isbn") String isbn, @RequestHeader(required = false) String authorization) {
        myLogger.info("getBook method launched");
        authorisationService.getAuthorisationLevel(DigibookyFeature.GET_BOOK, authorization);
        Book book = bookService.getOneBook(isbn);
        String userInformationString = "";
        if (UserRole.MEMBER.getAuthorisationLevel() <= authorisationService.getAuthorisationLevel(authorization)) {
            userInformationString = bookLoanService.getBookDetails(book);
        }
        EnhancedBookDto enhancedBookDto = bookMapper.mapBookToEnhancedDto(book, userInformationString);
        myLogger.info("getBook method finished");
        return enhancedBookDto;
    }

    /**
     * Story 3
     *
     * @param isbn
     * @return list of books containing (part of) isbn
     */
    @GetMapping(produces = "application/json", params = "isbn")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> search(@RequestParam String isbn, @RequestHeader(required = false) String authorization) {
        myLogger.info("search method by isbn " + isbn + " has been queried");
        authorisationService.getAuthorisationLevel(DigibookyFeature.SEARCH_BOOK_BY_ISBN, authorization);
        List<BookDto> bookDtosByIbn = bookService.searchBookByISBN(isbn).stream()
                .map(bookMapper::mapBookToDto)
                .toList();
        myLogger.info("search method by isbn " + isbn + " has been successfully finished");
        return bookDtosByIbn;
    }

    /**
     * @param bookDto
     * @param authorization return : adds a book to the list of books
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewBook(@RequestBody BookDto bookDto, @RequestHeader(required = false) String authorization) {
        myLogger.info("RegisterNewBook Method called");
        authorisationService.getAuthorisationLevel(DigibookyFeature.REGISTER_NEW_BOOK, authorization);
        Book book = bookMapper.mapBookDtoToBook(bookDto);
        bookService.registerBook(book);
        myLogger.info("RegisterNewBook Method successfully ended");
    }

    @GetMapping(path = "/search-by-title", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BookDto> searchBookByTitle(@RequestParam String title, @RequestHeader(required = false) String authorization){
        myLogger.info("Search book by title method is called");
        authorisationService.getAuthorisationLevel(DigibookyFeature.SEARCH_BOOK_BY_TITLE, authorization);
        Collection<Book> books = bookService.searchBooksByTitle(title);
        Collection<BookDto> bookDtos =  books.stream()
                .map(bookMapper::mapBookToDto)
                .collect(Collectors.toList());
        myLogger.info("Search book by title method successfully ended");
        return bookDtos;
    }

    /**
     * Story 5
     * @param authorName - enter author firstname or lastname
     * @return books written by given author
     */
    @GetMapping(path="/search-by-author", produces="application/json", params="authorName")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> searchBookByAuthor(@RequestParam() String authorName, @RequestHeader(required = false) String authorization){
        myLogger.info("search started for "+ authorName);
        authorisationService.getAuthorisationLevel(DigibookyFeature.SEARCH_BOOK_BY_AUTHOR, authorization);
        List<BookDto> listOfBooksByAuthorName =  bookService.searchBookByAuthor(authorName).stream()
                .map(bookMapper::mapBookToDto)
                .toList();
        myLogger.info("search completed for "+ authorName);
        return listOfBooksByAuthorName;
    }

    @PutMapping(path = "/update/{isbn}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto,
                              @RequestHeader(required = false) String authorization){
        myLogger.info("Updating book method called for isbn: " + isbn);
        authorisationService.getAuthorisationLevel(DigibookyFeature.UPDATE_BOOK, authorization);
        Book bookToUpdate = bookMapper.mapBookDtoToBook(bookDto);
        Book updatedBook = bookService.updateBook(isbn, bookToUpdate);
        BookDto updatedBookDto = bookMapper.mapBookToDto(updatedBook);
        myLogger.info("Updating succeeded for isbn " + updatedBookDto.getIsbn());
        return updatedBookDto;
    }

    @PutMapping(path = "/delete/{bookId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookDto deleteBook(@PathVariable("bookId") String bookId, @RequestHeader(required = false) String authorization){
        myLogger.info("Deleting book method called for bookId: " + bookId);
        authorisationService.getAuthorisationLevel(DigibookyFeature.DELETE_BOOK, authorization);
        Book book = bookService.deleteBookBy(bookId);
        BookDto bookDto = bookMapper.mapBookToDto(book);
        myLogger.info("Deleting succeeded for bookId " + bookId);
        return bookDto;
    }
}
