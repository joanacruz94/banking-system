package com.ironhack.server.model;

import com.ironhack.server.enums.Status;
import com.ironhack.server.util.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @Embedded
    protected Money balance;

    @Size(min=1, max=2)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "account_owner",
            joinColumns = { @JoinColumn(name = "account_id") },
            inverseJoinColumns = { @JoinColumn(name = "owner_id") }
    )
    protected List<AccountHolder> owners;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Status status;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    protected BigDecimal penaltyFee;

    @OneToMany(mappedBy = "accountFrom")
    protected List<Transaction> outcomes;

    @OneToMany(mappedBy = "accountTo")
    protected List<Transaction> incomes;

    public Account(BigDecimal balance, String currency) {
        this.balance = new Money(balance, currency);
        this.owners = new ArrayList<>();
        this.penaltyFee = new BigDecimal("40");
        this.status = Status.ACTIVE;
    }

    public void addOwner(AccountHolder accountHolder){
        this.owners.add(accountHolder);
    }

    // TODO -> check if is the same currency
    public void debitBalance(BigDecimal amount){
        this.balance.decreaseAmount(amount);
    }

    // TODO -> check if is the same currency
    public void creditBalance(BigDecimal amount){
        this.balance.increaseAmount(amount);
    }
}
