package com.ironhack.bankSystem.model;

import com.ironhack.bankSystem.enums.Status;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class StudentCheckingAccount extends CheckingAccount {

    public StudentCheckingAccount(BigDecimal balance, String currency, String secretKey) {
        super(balance, currency, secretKey);
    }
}
