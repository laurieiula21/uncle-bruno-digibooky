package com.switchfully.digibooky.unclebrunodigibooky.domain.user;

import com.switchfully.digibooky.unclebrunodigibooky.domain.Address;

public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String inss;
    private String email;
    private Address address;
    private String userRole;

    public String getId() {
        return id;
    }

    public UserDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getInss() {
        return inss;
    }

    public UserDto setInss(String inss) {
        this.inss = inss;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public UserDto setAddress(Address address) {
        this.address = address;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public UserDto setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

}
