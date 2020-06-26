package com.ironhack.bankSystem.service;

import com.ironhack.bankSystem.dto.CheckingAccountPostDTO;
import com.ironhack.bankSystem.dto.CreditCardAccountPostDTO;
import com.ironhack.bankSystem.dto.SavingsAccountPostDTO;
import com.ironhack.bankSystem.dto.TransactionGetDTO;
import com.ironhack.bankSystem.exceptions.AppException;
import com.ironhack.bankSystem.exceptions.NotFoundException;
import com.ironhack.bankSystem.model.*;
import com.ironhack.bankSystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RegularCheckingAccountRepository regularCheckingAccountRepository;

    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public Account createCheckingAccount(CheckingAccountPostDTO accountDTO){
        Long primaryOwnerID = accountDTO.getPrimaryOwnerID();
        Long secondaryOwnerID = accountDTO.getSecondaryOwnerID().isPresent() ? accountDTO.getSecondaryOwnerID().get() : null;
        AccountHolder primaryOwner = accountHolderRepository.findById(primaryOwnerID).orElseThrow(() -> new NotFoundException("Primary account holder ID doesn't exist!"));
        AccountHolder secondaryOwner = null;
        Account createdAccount;
        RegularCheckingAccount regularCheckingAcc = null;
        StudentCheckingAccount studentCheckingAcc = null;

        if (secondaryOwnerID != null) {
            secondaryOwner = accountHolderRepository.findById(secondaryOwnerID).orElseThrow(() -> new NotFoundException("Secondary account holder ID doesn't exist!"));
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfBirth = primaryOwner.getDateOfBirth();

        Period period = Period.between(dateOfBirth, currentDate);
        int periodYears = period.getYears();

        if (periodYears >= 24) {
            regularCheckingAcc = new RegularCheckingAccount(accountDTO.getBalance(), accountDTO.getCurrency(), accountDTO.getSecretKey());
            regularCheckingAcc.addOwner(primaryOwner);
            if (secondaryOwner != null) regularCheckingAcc.addOwner(secondaryOwner);
            createdAccount = regularCheckingAccountRepository.save(regularCheckingAcc);
        } else {
            studentCheckingAcc = new StudentCheckingAccount(accountDTO.getBalance(), accountDTO.getCurrency(), accountDTO.getSecretKey());
            studentCheckingAcc.addOwner(primaryOwner);
            if (secondaryOwner != null) studentCheckingAcc.addOwner(secondaryOwner);
            createdAccount = studentCheckingAccountRepository.save(studentCheckingAcc);
        }

        return createdAccount;
    }

    public Account createCreditCard(CreditCardAccountPostDTO accountDTO){
        Long primaryOwnerID = accountDTO.getPrimaryOwnerID();
        Long secondaryOwnerID = accountDTO.getSecondaryOwnerID().isPresent() ? accountDTO.getSecondaryOwnerID().get() : null;
        AccountHolder primaryOwner = accountHolderRepository.findById(primaryOwnerID).orElseThrow(() -> new NotFoundException("Primary account holder ID doesn't exist!"));
        AccountHolder secondaryOwner = null;

        if (secondaryOwnerID != null) {
            secondaryOwner = accountHolderRepository.findById(secondaryOwnerID).orElseThrow(() -> new NotFoundException("Secondary account holder ID doesn't exist!"));
        }

        CreditCardAccount creditAccount = new CreditCardAccount(accountDTO.getBalance(), accountDTO.getCurrency(), accountDTO.getCreditLimit(), accountDTO.getInterestRate());
        creditAccount.addOwner(primaryOwner);
        if (secondaryOwner != null) creditAccount.addOwner(secondaryOwner);

        return creditCardAccountRepository.save(creditAccount);
    }

    public Account createCreditCard(SavingsAccountPostDTO accountDTO){
        Long primaryOwnerID = accountDTO.getPrimaryOwnerID();
        Long secondaryOwnerID = accountDTO.getSecondaryOwnerID().isPresent() ? accountDTO.getSecondaryOwnerID().get() : null;
        AccountHolder primaryOwner = accountHolderRepository.findById(primaryOwnerID).orElseThrow(() -> new NotFoundException("Primary account holder ID doesn't exist!"));
        AccountHolder secondaryOwner = null;

        if (secondaryOwnerID != null) {
            secondaryOwner = accountHolderRepository.findById(secondaryOwnerID).orElseThrow(() -> new NotFoundException("Secondary account holder ID doesn't exist!"));
        }

        SavingsAccount savingsAccount = new SavingsAccount(accountDTO.getBalance(), accountDTO.getCurrency(), accountDTO.getSecretKey(), accountDTO.getMinimumBalance(), accountDTO.getInterestRate());
        savingsAccount.addOwner(primaryOwner);
        if (secondaryOwner != null) savingsAccount.addOwner(secondaryOwner);

        return savingsAccountRepository.save(savingsAccount);
    }

    @Transactional
    public TransactionGetDTO transaction(Long accountIDFrom, Long accountIDTo, BigDecimal amount){
        if(accountIDFrom == accountIDTo) throw new RuntimeException("You are trying to transfer to the same account");

        Account accountFrom = accountRepository.findById(accountIDFrom).orElseThrow(() -> new NotFoundException("Account that you tying to transfer from doesn't exist"));
        Account accountTo = accountRepository.findById(accountIDTo).orElseThrow(() -> new NotFoundException("Account that you tying to transfer to doesn't exist"));

        List<Transaction> transactions = accountFrom.getIncomes();
        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        long timeBetween = ChronoUnit.SECONDS.between(lastTransaction.getTransactionDate(), LocalDateTime.now());
        if(timeBetween > 20) {
            if (accountFrom.getBalance().getBalance().add(amount).compareTo(BigDecimal.ZERO) >= 0) {
                accountFrom.debitBalance(amount);
                accountTo.creditBalance(amount);
            } else
                throw new AppException("Account from doesn' have enough funds to execute the transaction");
        } else {
            // Freeze account
        }

        return null;
    }

    public String showSavingsAccountBalance(Long id){
        SavingsAccount account = savingsAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account ID doesn't exist in the system"));
        return account.getBalance().toString();
    }

    public String showCheckingAccountBalance(Long id){
        RegularCheckingAccount account = regularCheckingAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account ID doesn't exist in the system"));
        return account.getBalance().toString();
    }

    public String showStudentCheckingAccountBalance(Long id){
        StudentCheckingAccount account = studentCheckingAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account ID doesn't exist in the system"));
        return account.getBalance().toString();
    }
}
