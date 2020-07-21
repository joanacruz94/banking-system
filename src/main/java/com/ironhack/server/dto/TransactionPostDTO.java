package com.ironhack.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPostDTO {
    private Long accountIDFrom;
    private Long accountIDTo;
    private BigDecimal amount;
    private String beneficiaryName;
}
