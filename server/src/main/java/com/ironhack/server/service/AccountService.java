package com.ironhack.server.service;

import com.ironhack.server.dto.CheckingAccountPostDTO;
import com.ironhack.server.dto.CreditCardAccountPostDTO;
import com.ironhack.server.dto.SavingsAccountPostDTO;
import com.ironhack.server.dto.TransactionGetDTO;
import com.ironhack.server.exceptions.AppException;
import com.ironhack.server.exceptions.NotFoundException;
import com.ironhack.server.model.*;
import com.ironhack.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
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

    @Autowired
    UserService userService;

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

    public Account createSavingsAccount(SavingsAccountPostDTO accountDTO){
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

    public List<Account> getAccounts(String filter){
        return accountRepository.findAll();
    }

    public Account findAccountById(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getDetails() + "DETAILS");
        System.out.println(auth.getAuthorities() + "ROLES");
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with that ID doesn't exist n the system"));
        /*if(user.getRoles().contains(RoleName.ROLE_ACCOUNTHOLDER)) {
            List<AccountHolder> owners = account.getOwners();
            List<Long> ids = owners.stream().map(owner -> owner.getId()).collect(Collectors.toList());
            if(!ids.contains(user.getId())) {
                throw new NotAuthorizeException("User is trying to access one account that is not of his own");
            }
        }*/
        return account;
    }
}
