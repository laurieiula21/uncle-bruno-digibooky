package com.switchfully.digibooky.unclebrunodigibooky.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BookLoan {
    private final String loanId;
    private final String bookId;
    private final String userId;
    private final LocalDate lentOutDate;
    private LocalDate returnDate;
    private List<Fine> fines;

    public BookLoan(String bookId, String userId, LocalDate lentOutDate) {
        this.loanId = UUID.randomUUID().toString();
        this.bookId = bookId;
        this.userId = userId;
        this.lentOutDate = lentOutDate;
    }

    public BookLoan setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public BookLoan setFines(List<Fine> fines) {
        this.fines = fines;
        return this;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getLentOutDate() {
        return lentOutDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public List<Fine> getFines() {
        return fines;
    }
}
