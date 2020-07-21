package com.ironhack.server.repository;

import com.ironhack.server.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transaction t\n" +
            "WHERE t.id_account_from = :accountID",
            nativeQuery = true)
    List<Transaction> findByIdAccountFrom(Long accountID);

    @Query(value = "SELECT * FROM transaction t\n" +
            "WHERE t.id_account_to = :accountID",
            nativeQuery = true)
    List<Transaction> findByIdAccountTo(Long accountID);

    @Query(value = "SELECT SUM(amount) FROM TRANSACTION\n" +
            "WHERE id_account_from = :accountID AND DATE(transaction_date) < :date\n" +
            "GROUP BY DAY(transaction_date)",
            nativeQuery = true)
    List<BigDecimal> findAmountsBeforeDate(Long accountID, String date);

    @Query(value = "SELECT SUM(amount) FROM TRANSACTION\n" +
            "WHERE id_account_from = :accountID AND DATE(transaction_date) = :date",
            nativeQuery = true)
    BigDecimal findAmountPerDate(Long accountID, String date);
}
