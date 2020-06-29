package com.ironhack.server.service;

import com.ironhack.server.dto.AccountHolderDTO;
import com.ironhack.server.dto.SignUpAccountHolderRequest;
import com.ironhack.server.enums.RoleName;
import com.ironhack.server.exceptions.AppException;
import com.ironhack.server.exceptions.ConflictException;
import com.ironhack.server.exceptions.NotFoundException;
import com.ironhack.server.model.AccountHolder;
import com.ironhack.server.model.Address;
import com.ironhack.server.model.Role;
import com.ironhack.server.repository.AccountHolderRepository;
import com.ironhack.server.repository.RoleRepository;
import com.ironhack.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LogManager.getLogger(AccountHolderService.class);

    public AccountHolderDTO findAccountHolderById(Long id){
        LOGGER.info("Look for account holder user with ID " + id);
        AccountHolder accountHolder = accountHolderRepository.findById(id).orElseThrow(() -> new NotFoundException("AccountController holder with that ID doesn't exist"));
        return new AccountHolderDTO(accountHolder.getId(), accountHolder.getName(), accountHolder.getEmail(),
                accountHolder.getDateOfBirth().getDayOfMonth() + " " + accountHolder.getDateOfBirth().getMonth() + " " + accountHolder.getDateOfBirth().getYear(),
                accountHolder.getPrimaryAddress(),
                accountHolder.getAccounts().stream().map(acc -> acc.getId()).collect(Collectors.toList()));
    }

    public List<AccountHolderDTO> findAllAccountHolders(){
        LOGGER.info("Look for all account holders users");
        return accountHolderRepository.findAll().stream().map(
                accountHolder -> new AccountHolderDTO(accountHolder.getId(), accountHolder.getName(), accountHolder.getEmail(),
                accountHolder.getDateOfBirth().getDayOfMonth() + " " + accountHolder.getDateOfBirth().getMonth() + " " + accountHolder.getDateOfBirth().getYear(),
                accountHolder.getPrimaryAddress(),
                accountHolder.getAccounts().stream().map(acc -> acc.getId()).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    public Long registerAccountHolder(SignUpAccountHolderRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Email [email: " + signUpRequest.getEmail() + "] is already taken");
        }

        Address address = new Address(signUpRequest.getCity(), signUpRequest.getCountry(), signUpRequest.getStreetAddress(), signUpRequest.getHouseNumber(), signUpRequest.getZipCode());
        String[] dateOfBirth = signUpRequest.getDateOfBirth().split("-");
        LocalDate birthOfHolder = LocalDate.of(Integer.parseInt(dateOfBirth[2]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[0]));
        AccountHolder accountHolder = new AccountHolder(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword(), birthOfHolder, address);
        accountHolder.setPassword(passwordEncoder.encode(accountHolder.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_ACCOUNTHOLDER)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));
        accountHolder.setRoles(Collections.singleton(userRole));

        LOGGER.info("Successfully registered user with [email: {}]", accountHolder.getEmail());

        return accountHolderRepository.save(accountHolder).getId();
    }
}
