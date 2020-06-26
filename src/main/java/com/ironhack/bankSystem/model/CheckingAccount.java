package com.ironhack.bankSystem.model;

import com.ironhack.bankSystem.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
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
}
