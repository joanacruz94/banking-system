package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.*;
import com.ironhack.server.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
