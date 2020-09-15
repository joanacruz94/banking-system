package com.server.service;

import com.server.dto.*;
import com.server.enums.Status;
import com.server.exceptions.AppException;
import com.server.exceptions.NotFoundException;
import com.server.model.*;
import com.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    public Long createCheckingAccount(CheckingAccountPostDTO accountDTO){
        LOGGER.info("[INIT] -> Create a checking account");
        Long primaryOwnerID = accountDTO.getPrimaryOwnerID();
        Long secondaryOwnerID = accountDTO.getSecondaryOwnerID().isPresent() ? accountDTO.getSecondaryOwnerID().get() : null;
        if(!Currency.getAvailableCurrencies().contains(Currency.getInstance(accountDTO.getCurrency()))) {
            throw new AppException("Currency doesn't exist");
        }
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
            LOGGER.info("Account will be a regular checking");
            regularCheckingAcc = new RegularCheckingAccount(accountDTO.getBalance(), accountDTO.getCurrency(), passwordEncoder.encode(accountDTO.getSecretKey()));
            regularCheckingAcc.addOwner(primaryOwner);
            if (secondaryOwner != null) regularCheckingAcc.addOwner(secondaryOwner);
            createdAccount = regularCheckingAccountRepository.save(regularCheckingAcc);
        } else {
            LOGGER.info("Account will be a student checking");
            studentCheckingAcc = new StudentCheckingAccount(accountDTO.getBalance(), accountDTO.getCurrency(), accountDTO.getSecretKey());
            studentCheckingAcc.addOwner(primaryOwner);
            if (secondaryOwner != null) studentCheckingAcc.addOwner(secondaryOwner);
            createdAccount = studentCheckingAccountRepository.save(studentCheckingAcc);
        }

        LOGGER.info("Successfully created checking account with [ID: {}]", createdAccount.getId());

        return createdAccount.getId();
    }

    public Long createCreditCard(CreditCardAccountPostDTO accountDTO){
        LOGGER.info("[INIT] -> Create a credit account");
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

        CreditCardAccount createdAccount = creditCardAccountRepository.save(creditAccount);
        LOGGER.info("Successfully created credit account with [ID: {}]", createdAccount.getId());
        return createdAccount.getId();
    }

    public Long createSavingsAccount(SavingsAccountPostDTO accountDTO){
        LOGGER.info("[INIT] -> Create a savings account");
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

        SavingsAccount createdAccount = savingsAccountRepository.save(savingsAccount);
        LOGGER.info("Successfully created savings account with [ID: {}]", createdAccount.getId());
        return createdAccount.getId();
    }

    public List<AccountGetDTO> getRegularCheckingAccounts(){
        return regularCheckingAccountRepository.findAll().stream().map(acc -> new RegularCheckingAccountGetDTO(
                acc.getId(),
                acc.getBalance().getBalance(),
                acc.getBalance().getCurrency().toString(),
                acc.getPenaltyFee(),
                acc.getStatus(),
                acc.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                acc.getSecretKey(),
                acc.getMinimumBalance(),
                acc.getMonthlyMaintenanceFee(),
                acc.getChargeFee()
        )).collect(Collectors.toList());
    }

    public List<AccountGetDTO> getStudentCheckingAccounts(){
        return studentCheckingAccountRepository.findAll().stream().map(acc -> new StudentCheckingAccountGetDTO(
                acc.getId(),
                acc.getBalance().getBalance(),
                acc.getBalance().getCurrency().toString(),
                acc.getPenaltyFee(),
                acc.getStatus(),
                acc.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                acc.getSecretKey()
        )).collect(Collectors.toList());
    }

    public List<AccountGetDTO> getSavingsAccounts(){
        return savingsAccountRepository.findAll().stream().map(acc -> new SavingsAccountGetDTO(
                acc.getId(),
                acc.getBalance().getBalance(),
                acc.getBalance().getCurrency().toString(),
                acc.getPenaltyFee(),
                acc.getStatus(),
                acc.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                acc.getSecretKey(),
                acc.getMinimumBalance(),
                acc.getInterestRate(),
                acc.getCreditDate()
        )).collect(Collectors.toList());
    }

    public List<AccountGetDTO> getCreditAccounts(){
        return creditCardAccountRepository.findAll().stream().map(acc -> new CreditCardAccountGetDTO(
                acc.getId(),
                acc.getBalance().getBalance(),
                acc.getBalance().getCurrency().toString(),
                acc.getPenaltyFee(),
                acc.getStatus(),
                acc.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                acc.getCreditLimit(),
                acc.getInterestRate(),
                acc.getDebitDate()
        )).collect(Collectors.toList());
    }

    public List<AccountGetDTO> getUserAccounts(Long ownerID){
        List<Object[]> accounts = accountRepository.findUserAccounts(ownerID);
        return accounts.stream().map(account -> new AccountGetDTO(
                Long.parseLong(account[0].toString()),
                new BigDecimal(account[1].toString()),
                account[2].toString(),
                new BigDecimal(account[3].toString()),
                Status.valueOf(account[4].toString()),
                Arrays.asList(account[5].toString())
        )).collect(Collectors.toList());
    }

    public List<AccountGetDTO> getAccounts(String filter){
        LOGGER.info("[INIT] -> Find all accounts");
        List<AccountGetDTO> accountsList = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().toArray()[0].toString().equals("ROLE_ADMIN")) {
            if (!filter.isEmpty()) {
                switch (filter) {
                    case "regularChecking":
                        accountsList = getRegularCheckingAccounts();
                        break;
                    case "studentChecking":
                        accountsList = getStudentCheckingAccounts();
                        break;
                    case "savings":
                        accountsList = getSavingsAccounts();
                        break;
                    case "credit":
                        accountsList = getCreditAccounts();
                        break;
                    default:
                        return accountsList;
                }
            } else {
                accountsList.addAll(getRegularCheckingAccounts());
                accountsList.addAll(getStudentCheckingAccounts());
                accountsList.addAll(getSavingsAccounts());
                accountsList.addAll(getCreditAccounts());
            }
        } else if(auth.getAuthorities().toArray()[0].toString().equals("ROLE_ACCOUNTHOLDER")) {
            User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new AppException("Error"));
            Long userID = user.getId();
            return getUserAccounts(userID);
        } else {
            LOGGER.error("[ERROR] -> Not authorized");
            throw new AppException("You are a third party user so you are not authorized to check accounts");
        }

        return accountsList;
    }

    public AccountGetDTO findAccountById(Long id){
        LOGGER.info("[INIT] -> Find account");
        AccountGetDTO accountGetDTO = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RegularCheckingAccount regularCheckingAccount = regularCheckingAccountRepository.findById(id).isPresent() ? regularCheckingAccountRepository.findById(id).get() : null;
        StudentCheckingAccount studentCheckingAccount = studentCheckingAccountRepository.findById(id).isPresent() ? studentCheckingAccountRepository.findById(id).get() : null;
        SavingsAccount savingsAccount = savingsAccountRepository.findById(id).isPresent() ? savingsAccountRepository.findById(id).get() : null;
        CreditCardAccount creditAccount = creditCardAccountRepository.findById(id).isPresent() ? creditCardAccountRepository.findById(id).get() : null;
        if(regularCheckingAccount != null) {
            LOGGER.info("Account is regular checking type");
            accountGetDTO = new RegularCheckingAccountGetDTO(
                    regularCheckingAccount.getId(),
                    regularCheckingAccount.getBalance().getBalance(),
                    regularCheckingAccount.getBalance().getCurrency().toString(),
                    regularCheckingAccount.getPenaltyFee(),
                    regularCheckingAccount.getStatus(),
                    regularCheckingAccount.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                    regularCheckingAccount.getSecretKey(),
                    regularCheckingAccount.getMinimumBalance(),
                    regularCheckingAccount.getMonthlyMaintenanceFee(),
                    regularCheckingAccount.getChargeFee()
            );
        } else if(creditAccount != null) {
            LOGGER.info("Account is credit type");
            accountGetDTO = new CreditCardAccountGetDTO(
                        creditAccount.getId(),
                        creditAccount.getBalance().getBalance(),
                        creditAccount.getBalance().getCurrency().toString(),
                        creditAccount.getPenaltyFee(),
                        creditAccount.getStatus(),
                        creditAccount.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                        creditAccount.getCreditLimit(),
                        creditAccount.getInterestRate(),
                        creditAccount.getDebitDate());
        } else if(studentCheckingAccount != null) {
            LOGGER.info("Account is student type");
            accountGetDTO = new StudentCheckingAccountGetDTO(
                    studentCheckingAccount.getId(),
                    studentCheckingAccount.getBalance().getBalance(),
                    studentCheckingAccount.getBalance().getCurrency().toString(),
                    studentCheckingAccount.getPenaltyFee(),
                    studentCheckingAccount.getStatus(),
                    studentCheckingAccount.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                    studentCheckingAccount.getSecretKey()
            );
        } else if(savingsAccount != null) {
            LOGGER.info("Account is savings type");
            accountGetDTO = new SavingsAccountGetDTO(
                    savingsAccount.getId(),
                    savingsAccount.getBalance().getBalance(),
                    savingsAccount.getBalance().getCurrency().toString(),
                    savingsAccount.getPenaltyFee(),
                    savingsAccount.getStatus(),
                    savingsAccount.getOwners().stream().map((owner) -> owner.getName()).collect(Collectors.toList()),
                    savingsAccount.getSecretKey(),
                    savingsAccount.getMinimumBalance(),
                    savingsAccount.getInterestRate(),
                    savingsAccount.getCreditDate()
            );
        }

        if(accountGetDTO == null) {
            LOGGER.error("[ERROR] -> Account is not found");
            throw new NotFoundException("Account with that ID doesn't exist in the system");
        } else {
            List<String> ownersNames = accountGetDTO.getOwnersNames();
            User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new NotFoundException("Error"));
            if(auth.getAuthorities().toArray()[0].toString().equals("ROLE_ACCOUNTHOLDER") && !ownersNames.contains(user.getName())) {
                LOGGER.error("[ERROR] -> Not authorized");
                throw new AppException("You cannot see information of an account that isn't yours!");
            }
        }

        return accountGetDTO;
    }

    public void creditAccount(Long accountID, BigDecimal amount){
        LOGGER.info("[INIT] -> Credit account");
        Account account = accountRepository.findById(accountID).orElseThrow(() -> new NotFoundException("Account with that ID doesn't exist in the system"));
        account.creditBalance(amount);
        accountRepository.save(account);
    }

    public void debitAccount(Long accountID, BigDecimal amount){
        LOGGER.info("[INIT] -> Debit account");
        Account account = accountRepository.findById(accountID).orElseThrow(() -> new NotFoundException("Account with that ID doesn't exist in the system"));
        if (account.getBalance().getBalance().add(amount).compareTo(BigDecimal.ZERO) >= 0) {
            account.debitBalance(amount);
            accountRepository.save(account);
        } else
            throw new AppException("Account doesn't have enough funds");
    }

    public BigDecimal getBalanceAccount(Long accountID){
        LOGGER.info("[INIT] -> Check account balance");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findById(accountID).orElseThrow(() -> new NotFoundException("Account with that ID doesn't exist in the system"));
        List<String> ownersNames = account.getOwners().stream().map(owner -> owner.getName()).collect(Collectors.toList());
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new NotFoundException("Error"));
        if(auth.getAuthorities().toArray()[0].toString().equals("ROLE_ACCOUNTHOLDER") && !ownersNames.contains(user.getName())) {
            LOGGER.error("[ERROR] -> Not authorized");
            throw new AppException("You cannot see information of an account that isn't yours!");
        }
        return account.getBalance().getBalance();
    }
}
