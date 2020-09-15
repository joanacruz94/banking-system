package com.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class SavingsAccount extends Account {
    @NotNull
    @Pattern(regexp = "[0-9]{9}")
    private String secretKey;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal minimumBalance;

    @NotNull
    @DecimalMax(value = "0.5")
    @Digits(integer = 1, fraction = 5)
    private BigDecimal interestRate;

    @NotNull
    private LocalDate creditDate;

    public SavingsAccount(BigDecimal balance, String currency, String secretKey, Optional<BigDecimal> minimumBalance, Optional<BigDecimal> interestRate) {
        super(balance, currency);
        this.secretKey = secretKey;
        this.minimumBalance = (minimumBalance.isPresent()) ? minimumBalance.get() : new BigDecimal("1000");
        this.interestRate = (interestRate.isPresent()) ? interestRate.get() : new BigDecimal("0.0025");
        LocalDate currentDate = LocalDate.now();
        this.creditDate = currentDate.plusYears(1);
    }

    public void creditInterestRate(){
        this.balance.increaseAmount(this.balance.getBalance().multiply(this.interestRate));
    }

    public void chargePenaltyFee(){
        this.balance.decreaseAmount(penaltyFee);
    }
}
