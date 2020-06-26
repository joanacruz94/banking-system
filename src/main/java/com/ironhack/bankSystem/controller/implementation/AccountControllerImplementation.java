package com.ironhack.bankSystem.controller.implementation;

import com.ironhack.bankSystem.controller.interfaces.AccountControllerInterface;
import com.ironhack.bankSystem.dto.*;
import com.ironhack.bankSystem.model.Account;
import com.ironhack.bankSystem.model.CreditCardAccount;
import com.ironhack.bankSystem.model.Transaction;
import com.ironhack.bankSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountControllerImplementation implements AccountControllerInterface {
    @Autowired
    AccountService accountService;

    @GetMapping("/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Long id){
        return null;
    }

    @GetMapping("/accounts/{filter}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts(@RequestParam String filter){
        // Filter -> checking, student checking, credit, savings
        return null;
    }

    @GetMapping("/accounts/{ownerID}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getUserAccounts(@PathVariable Long ownerID){
        // Filter -> checking, student checking, credit, savings
        return null;
    }

    @PostMapping("/account/checkingAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckingAccountGetDTO createCheckingAccount(@RequestBody CheckingAccountPostDTO accountDTO){
        return null;
    }

    @PostMapping("/account/savingsAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccountGetDTO createSavingsAccount(@RequestBody SavingsAccountPostDTO accountDTO){
        return null;
    }

    @PostMapping("/account/creditAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardAccountGetDTO createCreditAccount(@RequestBody CreditCardAccountPostDTO accountDTO){
        return null;
    }

    @GetMapping("/account/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public String getBalanceAccount(@PathVariable Long id){
        return null;
    }

    @PatchMapping("/account/{id}/credit/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String creditAccount(@PathVariable Long id, @PathVariable BigDecimal amount){
        return null;
    }

    @PatchMapping("/account/{id}/debit/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String debitAccount(@PathVariable Long id, @PathVariable BigDecimal amount){
        return null;
    }

    @PostMapping("/account/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionGetDTO executeTransaction(@RequestBody TransactionPostDTO transactionDTO){
        return null;
    }

}
