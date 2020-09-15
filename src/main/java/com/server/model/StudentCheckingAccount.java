package com.server.model;

import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.math.BigDecimal;

@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class StudentCheckingAccount extends CheckingAccount {

    public StudentCheckingAccount(BigDecimal balance, String currency, String secretKey) {
        super(balance, currency, secretKey);
    }
}
