package com.switchfully.digibooky.unclebrunodigibooky.domain;

import java.util.Objects;

public class Address {

    private final String streetName;
    private final int streetNumber;
    private final int postalCode;
    private final String cityName;

    public Address(String streetName, int streetNumber, int postalCode, String cityName) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return streetNumber == address.streetNumber && postalCode == address.postalCode && Objects.equals(streetName, address.streetName) && Objects.equals(cityName, address.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, postalCode, cityName);
    }
}
