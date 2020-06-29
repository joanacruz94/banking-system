package com.ironhack.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAccountPostDTO {
    @ApiModelProperty(required = true)
    private BigDecimal balance;

    @NotBlank
    private String currency;

    @ApiModelProperty(required = true)
    private Long primaryOwnerID;

    private Long secondaryOwnerID;
    private BigDecimal interestRate;
    private BigDecimal minimumBalance;

    @NotBlank
    private String secretKey;

    public Optional<Long> getSecondaryOwnerID() {
        return Optional.ofNullable(secondaryOwnerID);
    }

    public Optional<BigDecimal> getInterestRate() {
        return Optional.ofNullable(interestRate);
    }

    public Optional<BigDecimal> getMinimumBalance() {
        return Optional.ofNullable(minimumBalance);
    }
}
