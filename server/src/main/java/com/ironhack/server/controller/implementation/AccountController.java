package com.ironhack.server.controller.implementation;

import com.ironhack.server.controller.interfaces.AccountControllerInterface;
import com.ironhack.server.dto.*;
import com.ironhack.server.model.Account;
import com.ironhack.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController implements AccountControllerInterface {
    @Autowired
    AccountService accountService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccountById(@PathVariable Long id){
        return accountService.findAccountById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/accounts/{filter}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAccounts(@RequestParam String filter){
        return accountService.getAccounts(filter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/accounts/{ownerID}")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getUserAccounts(@PathVariable Long ownerID){
        // Filter -> checking, student checking, credit, savings
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/checkingAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckingAccount(@RequestBody CheckingAccountPostDTO accountDTO){
        return accountService.createCheckingAccount(accountDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/savingsAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createSavingsAccount(@RequestBody SavingsAccountPostDTO accountDTO){
        return accountService.createSavingsAccount(accountDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/creditAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCreditAccount(@RequestBody CreditCardAccountPostDTO accountDTO){
        return accountService.createCreditCard(accountDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public String getBalanceAccount(@PathVariable Long id){
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/credit/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String creditAccount(@PathVariable Long id, @PathVariable BigDecimal amount){
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/debit/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String debitAccount(@PathVariable Long id, @PathVariable BigDecimal amount){
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionGetDTO executeTransaction(@RequestBody TransactionPostDTO transactionDTO){
        return null;
    }

}
