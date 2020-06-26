package com.ironhack.bankSystem.controller.interfaces;

import com.ironhack.bankSystem.dto.*;
import com.ironhack.bankSystem.model.Account;
import com.ironhack.bankSystem.model.CreditCardAccount;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface AccountControllerInterface {
    Account getAccountById(Long id);
    List<Account> getAccounts(String filter);
    List<Account> getUserAccounts(Long ownerID);
    CheckingAccountGetDTO createCheckingAccount(CheckingAccountPostDTO accountDTO);
    CreditCardAccountGetDTO createCreditAccount(CreditCardAccountPostDTO accountDTO);
    SavingsAccountGetDTO createSavingsAccount(SavingsAccountPostDTO accountDTO);
    String getBalanceAccount(Long id);
    String creditAccount(Long id, BigDecimal amount);
    String debitAccount(Long id, BigDecimal amount);
    TransactionGetDTO executeTransaction(TransactionPostDTO transactionDTO);
}
