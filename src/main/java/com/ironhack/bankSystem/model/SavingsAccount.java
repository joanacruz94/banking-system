package com.ironhack.bankSystem.model;

import com.ironhack.bankSystem.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class SavingsAccount extends Account {
    @NotNull
    private String secretKey;

    @NotNull
    @DecimalMin(value = "100")
    @DecimalMax(value = "1000")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal minimumBalance;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Status status;

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
        this.status = Status.ACTIVE;
        this.interestRate = (interestRate.isPresent()) ? interestRate.get() : new BigDecimal("0.0025");
        LocalDate currentDate = LocalDate.now();
        this.creditDate = currentDate.plusYears(1);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getCreditDate() {
        return creditDate;
    }

    public void setCreditDate(LocalDate creditDate) {
        this.creditDate = creditDate;
    }

    public void creditInterestRate(){
        this.balance.increaseAmount(this.balance.getBalance().multiply(this.interestRate));
    }

    public void chargePenaltyFee(){
        this.balance.decreaseAmount(penaltyFee);
    }
}
