package com.ironhack.bankSystem.workers;

import com.ironhack.bankSystem.model.CheckingAccount;
import com.ironhack.bankSystem.model.RegularCheckingAccount;
import com.ironhack.bankSystem.model.SavingsAccount;
import com.ironhack.bankSystem.repository.RegularCheckingAccountRepository;
import com.ironhack.bankSystem.repository.SavingsAccountRepository;
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

    @Scheduled(cron = "0 0 12 * * ?")
    public void checkFraud() {
        // Get all accounts
        // To which account check ifUser

    }

}
