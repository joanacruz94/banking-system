package com.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class CheckingAccount extends Account {
    @NotNull
    @Pattern(regexp = "[0-9]{9}")
    private String secretKey;

    public CheckingAccount(BigDecimal balance, String currency, String secretKey) {
        super(balance, currency);
        this.secretKey = secretKey;
    }
}
