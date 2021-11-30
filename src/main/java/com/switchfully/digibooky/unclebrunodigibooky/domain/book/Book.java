package com.switchfully.digibooky.unclebrunodigibooky.domain.book;

import java.util.UUID;

public class Book {
    private final String id;
    private final String isbn;
    private final String title;
    private final Author author;
    private final String summary;

    public Book(String isbn, String title, Author author, String summary) {
        this.id = UUID.randomUUID().toString();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public String toString(){
        return isbn+"\n"+title+"\n"+author+"\n"+summary;

    }
}
