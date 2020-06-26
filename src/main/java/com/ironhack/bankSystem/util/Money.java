package com.ironhack.bankSystem.util;

import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Embeddable
public class Money implements Transactional {
    private static final Currency USD = Currency.getInstance("USD");
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private Currency currency;

    @Digits(integer = 15, fraction = 2)
    private BigDecimal balance;

    public Money(BigDecimal balance, Currency currency, RoundingMode rounding) {
        this.currency = currency;
        setBalance(balance.setScale(currency.getDefaultFractionDigits(), rounding));
    }

    public Money(BigDecimal balance, String currency) {
        this(balance, Currency.getInstance(currency), DEFAULT_ROUNDING);
    }

    public Money(BigDecimal balance) {
        this(balance, USD, DEFAULT_ROUNDING);
    }

    public BigDecimal increaseAmount(Money money) {
        setBalance(this.balance.add(money.balance));
        return this.balance;
    }

    public BigDecimal increaseAmount(BigDecimal amount) {
        setBalance(this.balance.add(amount));
        return this.balance;
    }

    public BigDecimal decreaseAmount(Money money) {
        setBalance(this.balance.subtract(money.getBalance()));
        return this.balance;
    }

    public BigDecimal decreaseAmount(BigDecimal amount) {
        setBalance(this.balance.subtract(amount));
        return this.balance;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    private void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return getCurrency().getSymbol() + " " + getBalance();
    }
}