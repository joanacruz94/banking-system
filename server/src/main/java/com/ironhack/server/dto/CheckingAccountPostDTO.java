package com.ironhack.server.dto;

import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.Optional;

public class CheckingAccountPostDTO {
    private BigDecimal balance;
    private String currency;
    private Long primaryOwnerID;
    private Optional<Long> secondaryOwnerID;
    private String secretKey;

    public CheckingAccountPostDTO(BigDecimal balance, String currency, Long primaryOwnerID, Optional<Long> secondaryOwnerID, String secretKey) {
        this.balance = balance;
        this.currency = currency;
        this.primaryOwnerID = primaryOwnerID;
        this.secondaryOwnerID = secondaryOwnerID;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
