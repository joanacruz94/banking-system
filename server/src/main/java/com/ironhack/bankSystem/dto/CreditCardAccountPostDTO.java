package com.ironhack.bankSystem.dto;

import java.math.BigDecimal;
import java.util.Optional;

public class CreditCardAccountPostDTO {
    private BigDecimal balance;
    private String currency;
    private Long primaryOwnerID;
    private Optional<Long> secondaryOwnerID;
    private Optional<BigDecimal> creditLimit;
    private Optional<BigDecimal> interestRate;

    public CreditCardAccountPostDTO(BigDecimal balance, String currency, Long primaryOwnerID, Optional<Long> secondaryOwnerID, Optional<BigDecimal> creditLimit, Optional<BigDecimal> interestRate) {
        this.balance = balance;
        this.currency = currency;
        this.primaryOwnerID = primaryOwnerID;
        this.secondaryOwnerID = secondaryOwnerID;
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getPrimaryOwnerID() {
        return primaryOwnerID;
    }

    public void setPrimaryOwnerID(Long primaryOwnerID) {
        this.primaryOwnerID = primaryOwnerID;
    }

    public Optional<Long> getSecondaryOwnerID() {
        return secondaryOwnerID;
    }

    public void setSecondaryOwnerID(Optional<Long> secondaryOwnerID) {
        this.secondaryOwnerID = secondaryOwnerID;
    }

    public Optional<BigDecimal> getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Optional<BigDecimal> creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Optional<BigDecimal> getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Optional<BigDecimal> interestRate) {
        this.interestRate = interestRate;
    }
}
