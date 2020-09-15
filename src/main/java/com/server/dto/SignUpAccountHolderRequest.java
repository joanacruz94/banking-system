package com.server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Getter
@Setter
public class SignUpAccountHolderRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String streetAddress;

    private Integer houseNumber;

    @NotBlank
    private String zipCode;
}
