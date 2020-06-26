package com.ironhack.bankSystem.repository;

import com.ironhack.bankSystem.enums.SystemRole;
import com.ironhack.bankSystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(SystemRole roleName);
}
