package com.ironhack.bankSystem.dto;

import java.math.BigDecimal;

public class TransactionPostDTO {
    private Long accountIDFrom;
    private Long accountIDTo;
    private BigDecimal amount;
    private String beneficiaryName;

    public TransactionPostDTO(Long accountIDFrom, Long accountIDTo, BigDecimal amount, String beneficiaryName) {
        this.accountIDFrom = accountIDFrom;
        this.accountIDTo = accountIDTo;
        this.amount = amount;
        this.beneficiaryName = beneficiaryName;
    }

    public Long getAccountIDFrom() {
        return accountIDFrom;
    }

    public void setAccountIDFrom(Long accountIDFrom) {
        this.accountIDFrom = accountIDFrom;
    }

    public Long getAccountIDTo() {
        return accountIDTo;
    }

    public void setAccountIDTo(Long accountIDTo) {
        this.accountIDTo = accountIDTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }
}
