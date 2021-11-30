package com.switchfully.digibooky.unclebrunodigibooky.domain.book;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(isbn, bookDto.isbn) && Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author) && Objects.equals(summary, bookDto.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, summary);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", summary='" + summary + '\'' +
                '}';
    }
}
