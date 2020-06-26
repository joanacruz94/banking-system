package com.ironhack.bankSystem.dto;

import java.util.Optional;

public class AccountHolderPostDTO {
    // TODO -> check if is better to separate by 3 strings
    private String name;
    private String dateOfBirth;
    private String city;
    private String country;
    private String streetAddress;
    private Integer houseNumber;
    private String zipCode;
    private Optional<String> email;

    public AccountHolderPostDTO(String name, String dateOfBirth, String city, String country, String streetAddress, Integer houseNumber, String zipCode, Optional<String> email) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
        this.streetAddress = streetAddress;
        this.houseNumber = houseNumber;
        this.zipCode = zipCode;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }
}
