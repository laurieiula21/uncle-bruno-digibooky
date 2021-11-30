package com.switchfully.digibooky.unclebrunodigibooky.domain.book;

public class BookDto {
    private final String id;
    private final String isbn;
    private final String title;
    private final Author author;
    private final String summary;

    public BookDto(String id, String isbn, String title, Author author, String summary) {
        this.id = id;
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
}
