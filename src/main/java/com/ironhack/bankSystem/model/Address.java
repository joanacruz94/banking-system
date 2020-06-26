package com.ironhack.bankSystem.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Embeddable
public class Address {
    @Size(max=30)
    private String city;

    @Size(max=60)
    private String country;

    private String streetAddress;

    @PositiveOrZero
    private Integer houseNumber;

    @Size(max=15)
    private String zipCode;

    public Address(String city, String country, String streetAddress, Integer houseNumber, String zipCode) {
        this.city = city;
        this.country = country;
        this.streetAddress = streetAddress;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
    }
}
