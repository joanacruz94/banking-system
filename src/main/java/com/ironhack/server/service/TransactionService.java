package com.ironhack.server.service;

import com.ironhack.server.dto.TransactionGetDTO;
import com.ironhack.server.dto.TransactionPostDTO;
import com.ironhack.server.enums.Status;
import com.ironhack.server.exceptions.AppException;
import com.ironhack.server.exceptions.NotFoundException;
import com.ironhack.server.model.Account;
import com.ironhack.server.model.Transaction;
import com.ironhack.server.repository.AccountRepository;
import com.ironhack.server.repository.TransactionRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    private static final Logger LOGGER = LogManager.getLogger(TransactionService.class);


    public List<TransactionGetDTO> getAllTransactions(){
        return transactionRepository.findAll().stream().map((transaction) -> new TransactionGetDTO(transaction.getId(),
                transaction.getAccountFrom().getId(), transaction.getAccountTo().getId(), transaction.getAmount(), transaction.getTransactionDate().toString())).collect(Collectors.toList());
    }

    public List<TransactionGetDTO> getTransactionsAccountSent(Long accountID){
        return transactionRepository.findByIdAccountFrom(accountID).stream().map((transaction) -> new TransactionGetDTO(transaction.getId(),
                transaction.getAccountFrom().getId(), transaction.getAccountTo().getId(), transaction.getAmount(), transaction.getTransactionDate().toString())).collect(Collectors.toList());
    }

    public List<TransactionGetDTO> getTransactionsAccountReceived(Long accountID){
        return transactionRepository.findByIdAccountTo(accountID).stream().map((transaction) -> new TransactionGetDTO(transaction.getId(),
                transaction.getAccountFrom().getId(), transaction.getAccountTo().getId(), transaction.getAmount(), transaction.getTransactionDate().toString())).collect(Collectors.toList());
    }

    public List<TransactionGetDTO> getTransactionsAccount(Long accountID, String filter){
        if(!filter.isEmpty()) {
            if(filter.equals("sent")) {
                return getTransactionsAccountSent(accountID);
            } else if (filter.equals("received")){
                return getTransactionsAccountReceived(accountID);
            }
        }
        List<TransactionGetDTO> transactionsList = new ArrayList<>();
        transactionsList.addAll(getTransactionsAccountReceived(accountID));
        transactionsList.addAll(getTransactionsAccountSent(accountID));
        return transactionsList;
    }

    @Transactional
    public TransactionGetDTO transaction(TransactionPostDTO transactionDTO){
        LOGGER.info("[INIT] -> Transaction started");
        Transaction transaction;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account accountFrom = accountRepository.findById(transactionDTO.getAccountIDFrom()).orElseThrow(() -> new NotFoundException("Account that you tying to transfer from doesn't exist"));
        Account accountTo = accountRepository.findById(transactionDTO.getAccountIDTo()).orElseThrow(() -> new NotFoundException("Account that you tying to transfer to doesn't exist"));
        List<String> ownersNames = accountFrom.getOwners().stream().map(owner -> owner.getEmail()).collect(Collectors.toList());
        if(!ownersNames.contains(auth.getName())) {
            LOGGER.error("[ERROR] -> Not authorized");
            throw new AppException("Not authorized. Not yours account");
        }
        if(transactionDTO.getAccountIDFrom() == transactionDTO.getAccountIDTo()) {
            LOGGER.error("[ERROR] -> Trying to transfer to the same account");
            throw new AppException("You are trying to transfer to the same account");
        }

        List<Transaction> transactions = accountFrom.getIncomes();
        if(transactions.size() > 0) {
            Transaction lastTransaction = transactions.get(transactions.size() - 1);
            long timeBetween = ChronoUnit.SECONDS.between(lastTransaction.getTransactionDate(), LocalDateTime.now());
            String currentDate = LocalDate.now().toString();
            List<BigDecimal> averageSpent = transactionRepository.findAmountsBeforeDate(accountFrom.getId(), currentDate);
            BigDecimal spentCurrentDate = transactionRepository.findAmountPerDate(accountFrom.getId(), currentDate);
            spentCurrentDate.add(transactionDTO.getAmount());
            if (timeBetween < 1) {
                accountFrom.setStatus(Status.FROZEN);
                accountRepository.save(accountFrom);
                LOGGER.error("[ERROR] -> Fraud Detection");
                throw new AppException("Fraud detection: Your account was frozen since transactions are made with one second between");
            }
            for(BigDecimal value : averageSpent) {
                if(spentCurrentDate.add(spentCurrentDate.multiply(new BigDecimal("1.5"))).compareTo(value) > 0) {
                    LOGGER.error("[ERROR] -> Fraud Detection");
                    throw new AppException("Fraud detection: Your account was frozen since transactions are made with one second between");
                }
            }
        }

        if (accountFrom.getBalance().getBalance().subtract(transactionDTO.getAmount()).compareTo(BigDecimal.ZERO) >= 0) {
            accountFrom.debitBalance(transactionDTO.getAmount());
            accountTo.creditBalance(transactionDTO.getAmount());
            transaction = new Transaction(accountFrom, accountTo, transactionDTO.getAmount());
            Transaction createdTransaction = transactionRepository.save(transaction);
            LOGGER.info("Successfully created transaction with [ID: {}]" + createdTransaction.getId());
        } else {
            LOGGER.error("[ERROR] -> Not enough balance");
            throw new AppException("Account from doesn't have enough funds to execute the transaction");
        }

        return new TransactionGetDTO(transaction.getId(), transaction.getAccountFrom().getId(), transaction.getAccountTo().getId(), transaction.getAmount(), transaction.getTransactionDate().toString());
    }
}
