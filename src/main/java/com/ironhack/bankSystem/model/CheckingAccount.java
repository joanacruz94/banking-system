package com.ironhack.bankSystem.model;

import com.ironhack.bankSystem.enums.Status;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class CheckingAccount extends Account {
    @NotNull
    private String secretKey;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Status status;

    public CheckingAccount(BigDecimal balance, String currency, String secretKey) {
        super(balance, currency);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
