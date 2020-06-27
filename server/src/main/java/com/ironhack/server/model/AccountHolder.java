package com.ironhack.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class AccountHolder extends User {
    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    @ManyToMany(mappedBy = "owners")
    private List<Account> accounts;

    public AccountHolder(String name, String email, String password, LocalDate dateOfBirth, Address primaryAddress) {
        super(name, email, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.accounts = new ArrayList<>();
    }
}
