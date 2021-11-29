package com.switchfully.digibooky.unclebrunodigibooky.service;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Book;
import com.switchfully.digibooky.unclebrunodigibooky.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book showDetailsOfBook(String isbn){
        for(Book b : bookRepository.getAllBooks()){
            if(b.getIsbn().equals(isbn)){
                return b;
            }
        }
        return null;
    }

}
