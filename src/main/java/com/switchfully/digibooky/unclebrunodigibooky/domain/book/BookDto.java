package com.switchfully.digibooky.unclebrunodigibooky.domain.book;

public class BookDto {
    private String id;
    private String isbn;
    private String title;
    private Author author;
    private String summary;

    public BookDto setId(String id) {
        this.id = id;
        return this;
    }

    public BookDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookDto setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public BookDto setSummary(String summary) {
        this.summary = summary;
        return this;
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


}
