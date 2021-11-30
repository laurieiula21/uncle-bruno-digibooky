package com.switchfully.digibooky.unclebrunodigibooky.api.mapper;

import com.switchfully.digibooky.unclebrunodigibooky.domain.book.BookDto;
import com.switchfully.digibooky.unclebrunodigibooky.domain.book.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto mapBookToDto(Book book) {
        return new BookDto(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor(), book.getSummary());
    }
}
