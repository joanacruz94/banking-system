package com.ironhack.server.repository;

import com.ironhack.server.model.RegularCheckingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularCheckingAccountRepository extends JpaRepository<RegularCheckingAccount, Long> {

}
