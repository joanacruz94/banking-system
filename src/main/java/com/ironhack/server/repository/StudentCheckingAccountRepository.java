package com.ironhack.server.repository;

import com.ironhack.server.model.StudentCheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingAccountRepository extends JpaRepository<StudentCheckingAccount, Long> {
}
