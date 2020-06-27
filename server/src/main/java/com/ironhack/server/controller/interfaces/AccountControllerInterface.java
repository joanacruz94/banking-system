package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.*;
import com.ironhack.server.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountControllerInterface {
    Account getAccountById(Long id);
    List<Account> getAccounts(String filter);
    List<Account> getUserAccounts(Long ownerID);
    Account createCheckingAccount(CheckingAccountPostDTO accountDTO);
    Account createCreditAccount(CreditCardAccountPostDTO accountDTO);
    Account createSavingsAccount(SavingsAccountPostDTO accountDTO);
    String getBalanceAccount(Long id);
    String creditAccount(Long id, BigDecimal amount);
    String debitAccount(Long id, BigDecimal amount);
    TransactionGetDTO executeTransaction(TransactionPostDTO transactionDTO);
}
