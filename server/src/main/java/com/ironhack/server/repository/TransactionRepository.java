package com.ironhack.server.repository;

import com.ironhack.server.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
