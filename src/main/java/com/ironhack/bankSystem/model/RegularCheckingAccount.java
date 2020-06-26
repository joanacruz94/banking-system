package com.ironhack.bankSystem.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "account_id")
public class RegularCheckingAccount extends CheckingAccount{
    @NotNull
    @Digits(integer = 4, fraction = 2)
    private BigDecimal minimumBalance;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    private BigDecimal monthlyMaintenanceFee;

    @NotNull
    private LocalDate chargeFee;

    public RegularCheckingAccount(BigDecimal balance, String currency, String secretKey) {
        super(balance, currency, secretKey);
        this.minimumBalance = new BigDecimal("250");
        this.monthlyMaintenanceFee = new BigDecimal("12");
        this.chargeFee = LocalDate.now().plusMonths(1);
    }

    public LocalDate getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(LocalDate chargeFee) {
        this.chargeFee = chargeFee;
    }

    public void chargeMonthlyFee(){
        this.balance.decreaseAmount(monthlyMaintenanceFee);
    }

    public void chargePenaltyFee(){
        this.balance.decreaseAmount(penaltyFee);
    }
}
