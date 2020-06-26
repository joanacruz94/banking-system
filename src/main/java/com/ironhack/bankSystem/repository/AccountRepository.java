package com.ironhack.bankSystem.repository;

import com.ironhack.bankSystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
