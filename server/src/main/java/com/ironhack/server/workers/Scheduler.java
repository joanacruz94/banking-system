package com.ironhack.server.workers;

import com.ironhack.server.model.CreditCardAccount;
import com.ironhack.server.model.RegularCheckingAccount;
import com.ironhack.server.model.SavingsAccount;
import com.ironhack.server.repository.CreditCardAccountRepository;
import com.ironhack.server.repository.RegularCheckingAccountRepository;
import com.ironhack.server.repository.SavingsAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Scheduler {
    @Autowired
    SavingsAccountRepository savingsAccountRepository;

    @Autowired
    RegularCheckingAccountRepository regularCheckingAccountRepository;

    @Autowired
    CreditCardAccountRepository creditCardAccountRepository;

    @Scheduled(cron = "0 0 12 * * ?")
    public void creditSavingsAccount() {
        LocalDate currentDate = LocalDate.now();
        List<SavingsAccount> accounts = savingsAccountRepository.findAll();
        List<SavingsAccount> updatedAccounts = accounts.stream().filter((acc) -> acc.getCreditDate().isEqual(currentDate))
                .map((acc) -> {
                    acc.creditInterestRate();
                    acc.setCreditDate(currentDate.plusYears(1));
                    return acc;
                }).collect(Collectors.toList());
        savingsAccountRepository.saveAll(updatedAccounts);
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void debitCreditAccounts() {
        LocalDate currentDate = LocalDate.now();
        List<CreditCardAccount> accounts = creditCardAccountRepository.findAll();
        List<CreditCardAccount> updatedAccounts = accounts.stream().filter((acc) -> acc.getDebitDate().isEqual(currentDate))
                .map((acc) -> {
                    acc.debitInterestRate();
                    acc.setDebitDate(currentDate.plusMonths(1));
                    return acc;
                }).collect(Collectors.toList());
        creditCardAccountRepository.saveAll(updatedAccounts);
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void chargeFee() {
        LocalDate currentDate = LocalDate.now();
        List<RegularCheckingAccount> accounts = regularCheckingAccountRepository.findAll();
        List<RegularCheckingAccount> updatedAccounts =  accounts.stream().filter((acc) -> acc.getChargeFee().getMonth().equals(currentDate.getMonth()))
                .map((acc) -> {
                    acc.chargeMonthlyFee();
                    acc.setChargeFee(currentDate.plusMonths(1));
                    return acc;
                }).collect(Collectors.toList());
        regularCheckingAccountRepository.saveAll(updatedAccounts);
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void chargePenaltyFee() {
        LocalDate currentDate = LocalDate.now();
        List<RegularCheckingAccount> checkingAccounts = regularCheckingAccountRepository.findAll();
        List<SavingsAccount> savingsAccounts = savingsAccountRepository.findAll();

        List<RegularCheckingAccount> updatedCheckingAccounts = checkingAccounts.stream().filter((acc) -> acc.getBalance().getBalance().compareTo(acc.getMinimumBalance()) > 0)
                .map(acc -> {
                    acc.chargePenaltyFee();
                    return acc;
                }).collect(Collectors.toList());

        List<SavingsAccount> updatedSavingsAccounts =savingsAccounts.stream().filter((acc) -> acc.getBalance().getBalance().compareTo(acc.getMinimumBalance()) > 0)
                .map(acc -> {
                    acc.chargePenaltyFee();
                    return acc;
                }).collect(Collectors.toList());

        regularCheckingAccountRepository.saveAll(updatedCheckingAccounts);
        savingsAccountRepository.saveAll(updatedSavingsAccounts);
    }



}
