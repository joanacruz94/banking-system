package com.ironhack.server.model;

import com.ironhack.server.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
