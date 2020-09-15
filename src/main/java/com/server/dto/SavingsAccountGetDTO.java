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
public class SavingsAccountGetDTO extends AccountGetDTO{
    private final AccountType type = AccountType.SAVINGS;
    private String secretKey;
    private BigDecimal minimumBalance;
    private BigDecimal interestRate;
    private String creditDate;

    public SavingsAccountGetDTO(Long id, BigDecimal balance, String currency, BigDecimal penaltyFee, Status status, List<String> ownersNames, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate, LocalDate creditDate) {
        super(id, balance, currency, penaltyFee, status, ownersNames);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.creditDate = creditDate.getDayOfMonth() + " " + creditDate.getMonth() + " " + creditDate.getYear();
    }
}
