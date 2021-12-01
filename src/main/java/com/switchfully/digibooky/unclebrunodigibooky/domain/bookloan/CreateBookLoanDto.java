package com.switchfully.digibooky.unclebrunodigibooky.domain.bookloan;

public class CreateBookLoanDto {
    private String isbn;
    private String userId;

    public CreateBookLoanDto setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public CreateBookLoanDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getUserId() {
        return userId;
    }
}
