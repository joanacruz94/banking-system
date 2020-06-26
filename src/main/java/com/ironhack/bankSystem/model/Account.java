package com.ironhack.bankSystem.model;

import com.ironhack.bankSystem.util.Money;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public List<AccountHolder> getOwners() {
        return owners;
    }

    public void setOwners(List<AccountHolder> owners) {
        this.owners = owners;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
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

    public List<Transaction> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<Transaction> outcomes) {
        this.outcomes = outcomes;
    }

    public List<Transaction> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Transaction> incomes) {
        this.incomes = incomes;
    }
}
