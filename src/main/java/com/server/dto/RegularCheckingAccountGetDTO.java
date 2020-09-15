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
public class RegularCheckingAccountGetDTO extends AccountGetDTO {
    private final AccountType type = AccountType.REGULAR_CHECKING;
    private String secretKey;
    private BigDecimal minimumBalance;
    private BigDecimal monthlyMaintenanceFee;
    private String chargeFee;

    public RegularCheckingAccountGetDTO(Long id, BigDecimal balance, String currency, BigDecimal penaltyFee, Status status, List<String> ownersNames, String secretKey, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee, LocalDate chargeFee) {
        super(id, balance, currency, penaltyFee, status, ownersNames);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.chargeFee = chargeFee.getDayOfMonth() + " " + chargeFee.getMonth() + " " + chargeFee.getYear();
    }
}
