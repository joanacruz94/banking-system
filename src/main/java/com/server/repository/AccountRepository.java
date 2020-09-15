package com.server.repository;

import com.server.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT a.*, u.name FROM account a\n" +
            "JOIN account_owner ao ON a.id = ao.account_id\n" +
            "JOIN account_holder ah ON ao.owner_id = ah.user_id\n" +
            "JOIN user u ON ah.user_id = u.id\n" +
            "WHERE ah.user_id = :userID",
            nativeQuery = true)
    List<Object[]> findUserAccounts(Long userID);
}
