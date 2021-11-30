package com.switchfully.digibooky.unclebrunodigibooky.api.mapper;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto mapBookToDto(Book book) {
        return new BookDto()
                .setId(book.getId())
                .setIsbn(book.getIsbn())
                .setAuthor(book.getAuthor())
                .setTitle(book.getTitle())
                .setSummary(book.getSummary());
    }
}
