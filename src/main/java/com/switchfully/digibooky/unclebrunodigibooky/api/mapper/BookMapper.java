package com.switchfully.digibooky.unclebrunodigibooky.api.mapper;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.EnhancedBookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan.BookLoan;
import com.switchfully.digibooky.unclebrunodigibooky.domain.user.User;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookLoanService;
import com.switchfully.digibooky.unclebrunodigibooky.service.BookService;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private BookRepository bookRepository;
    private BookLoan bookLoan;
    private BookLoanService bookLoanService;
    private User user;

    public BookDto mapBookToDto(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setIsbn(book.getIsbn())
                .setAuthor(book.getAuthor())
                .setTitle(book.getTitle())
                .setSummary(book.getSummary());
    }

    public Book mapBookDtoToBook(BookDto bookDto) {
        return new Book(bookDto.getIsbn()
                , bookDto.getTitle()
                , bookDto.getAuthor()
                , bookDto.getSummary());
    }

    public EnhancedBookDto mapBookToEnhancedDto(Book book, String userName) {

        return new EnhancedBookDto()
                .setId(book.getId())
                .setIsbn(book.getIsbn())
                .setAuthor(book.getAuthor())
                .setTitle(book.getTitle())
                .setSummary(book.getSummary())
                .setLenderUserName(userName);
    }

}
