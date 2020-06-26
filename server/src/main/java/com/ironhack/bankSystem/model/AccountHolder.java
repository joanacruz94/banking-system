package com.ironhack.bankSystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class AccountHolder extends User {
    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    @Email
    private String mailingAddress;

    @ManyToMany(mappedBy = "owners")
    private List<Account> accounts;

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address primaryAddress, Optional<String> mailingAddress) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress.get();
        this.accounts = new ArrayList<>();
    }
}
