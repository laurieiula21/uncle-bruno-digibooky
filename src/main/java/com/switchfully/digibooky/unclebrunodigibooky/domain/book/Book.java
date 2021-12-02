package com.switchfully.digibooky.unclebrunodigibooky.domain.book;

import java.util.UUID;

public class Book {
    private final String id;
    private final String isbn;
    private String title;
    private Author author;
    private String summary;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void updateAuthor(String firstname, String lastname){
        author.setFirstName(firstname);
        author.setLastName(lastname);
    }


    @Override
    public String toString(){
        return isbn+"\n"+title+"\n"+author+"\n"+summary;

    }
}
