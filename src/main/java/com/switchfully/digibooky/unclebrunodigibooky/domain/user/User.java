package com.switchfully.digibooky.unclebrunodigibooky.domain.user;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;

public class User {

    private final String firstName;
    private final String lastName;
    private final String inss;
    private final String email;
    private final Address address;
    private final UserRole userRole;

    public User(String firstName, String lastName, String inss, String email, Address address, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.inss = inss;
        this.email = email;
        this.address = address;
        this.userRole = userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInss() {
        return inss;
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
}
