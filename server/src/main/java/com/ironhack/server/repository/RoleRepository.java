package com.ironhack.server.repository;

import com.ironhack.server.enums.RoleName;
import com.ironhack.server.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}
