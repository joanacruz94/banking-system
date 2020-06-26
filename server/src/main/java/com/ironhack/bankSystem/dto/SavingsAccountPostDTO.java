package com.ironhack.bankSystem.dto;

import java.math.BigDecimal;
import java.util.Optional;

public class SavingsAccountPostDTO {
    private BigDecimal balance;
    private String currency;
    private Long primaryOwnerID;
    private Optional<Long> secondaryOwnerID;
    private Optional<BigDecimal> interestRate;
    private Optional<BigDecimal> minimumBalance;
    private String secretKey;

    public SavingsAccountPostDTO(BigDecimal balance, String currency, Long primaryOwnerID, Optional<Long> secondaryOwnerID, Optional<BigDecimal> interestRate, Optional<BigDecimal> minimumBalance, String secretKey) {
        this.balance = balance;
        this.currency = currency;
        this.primaryOwnerID = primaryOwnerID;
        this.secondaryOwnerID = secondaryOwnerID;
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
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

    public Optional<BigDecimal> getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Optional<BigDecimal> interestRate) {
        this.interestRate = interestRate;
    }

    public Optional<BigDecimal> getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Optional<BigDecimal> minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
