package com.server.interfaces;

import com.server.dto.AccountGetDTO;
import com.server.dto.CheckingAccountPostDTO;
import com.server.dto.CreditCardAccountPostDTO;
import com.server.dto.SavingsAccountPostDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountInterface {
    AccountGetDTO getAccountById(Long id);
    List<AccountGetDTO> getAccounts(String filter);
    List<AccountGetDTO> getUserAccounts(Long ownerID);
    Long createCheckingAccount(CheckingAccountPostDTO accountDTO);
    Long createCreditAccount(CreditCardAccountPostDTO accountDTO);
    Long createSavingsAccount(SavingsAccountPostDTO accountDTO);
    BigDecimal getBalanceAccount(Long id);
    void creditAccount(Long id, BigDecimal amount);
    void debitAccount(Long id, BigDecimal amount);
}
