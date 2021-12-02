package com.switchfully.digibooky.unclebrunodigibooky.domain.user;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;

import java.util.UUID;

public class User {

    private final String id;
    private final String inss;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Address address;
    private UserRole userRole;

    public User(String inss, String firstName, String lastName, String email, Address address, UserRole userRole) {
        this.inss = inss;
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.userRole = userRole;
    }


    public String getId() {
        return id;
    }

    public String getInss() {
        return inss;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }


    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public User setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }
}
