package com.ironhack.bankSystem.repository;

import com.ironhack.bankSystem.model.RegularCheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularCheckingAccountRepository extends JpaRepository<RegularCheckingAccount, Long> {

}
