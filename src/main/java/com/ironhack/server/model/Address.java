package com.ironhack.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Address {
    @NotNull
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
