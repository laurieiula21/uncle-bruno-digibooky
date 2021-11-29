package com.switchfully.digibooky.unclebrunodigibooky.domain;

import java.util.UUID;

public class Author {
    private final String id;
    private final String lastName;
    private final String firstName;

    public Author(String lastName, String firstName) {
        this.id = UUID.randomUUID().toString();
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
