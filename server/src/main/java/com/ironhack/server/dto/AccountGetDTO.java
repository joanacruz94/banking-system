package com.ironhack.server.dto;

import com.ironhack.server.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountGetDTO {
    protected Long id;
    protected BigDecimal balance;
    protected String currency;
    protected BigDecimal penaltyFee;
    protected Status status;
    protected List<String> ownersNames;
}
