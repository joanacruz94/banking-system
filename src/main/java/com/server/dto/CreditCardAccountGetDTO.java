package com.server.dto;

import com.server.enums.AccountType;
import com.server.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardAccountGetDTO extends AccountGetDTO {
    private final AccountType type = AccountType.CREDIT;
    private BigDecimal creditLimit;
    private BigDecimal interestRate;
    private String debitDate;

    public CreditCardAccountGetDTO(Long id, BigDecimal balance, String currency, BigDecimal penaltyFee, Status status, List<String> ownersNames, BigDecimal creditLimit, BigDecimal interestRate, LocalDate debitDate) {
        super(id, balance, currency,penaltyFee, status, ownersNames);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
        this.debitDate = debitDate.getDayOfMonth() + " " + debitDate.getMonth() + " " + debitDate.getYear();
    }
}
