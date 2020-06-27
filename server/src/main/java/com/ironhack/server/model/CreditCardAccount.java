package com.ironhack.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class CreditCardAccount extends Account {
    @DecimalMax(value = "100000")
    @DecimalMin(value = "100")
    @Digits(integer = 6, fraction = 3)
    @NotNull
    private BigDecimal creditLimit;

    @DecimalMax(value = "0.2")
    @DecimalMin(value = "0.1")
    @Digits(integer = 1, fraction = 3)
    @NotNull
    private BigDecimal interestRate;

    @NotNull
    private LocalDate debitDate;

    public CreditCardAccount(BigDecimal balance, String currency, Optional<BigDecimal> creditLimit, Optional<BigDecimal> interestRate) {
        super(balance, currency);
        this.creditLimit = (creditLimit.isPresent()) ? creditLimit.get() : new BigDecimal("100");
        this.interestRate = (interestRate.isPresent()) ? interestRate.get() : new BigDecimal("0.2");
        LocalDate currentDate = LocalDate.now();
        this.debitDate = currentDate.plusYears(1);
    }
}
