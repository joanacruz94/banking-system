package com.ironhack.bankSystem.service;

import com.ironhack.bankSystem.dto.AccountHolderPostDTO;
import com.ironhack.bankSystem.model.AccountHolder;
import com.ironhack.bankSystem.model.Address;
import com.ironhack.bankSystem.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    public AccountHolder createAccountHolder(AccountHolderPostDTO accountHolderDTO){
        Address address = new Address(accountHolderDTO.getCity(), accountHolderDTO.getCountry(), accountHolderDTO.getStreetAddress(), accountHolderDTO.getHouseNumber(), accountHolderDTO.getZipCode());
        String[] dateOfBirth = accountHolderDTO.getDateOfBirth().split("-");
        LocalDate birthOfHolder = LocalDate.of(Integer.parseInt(dateOfBirth[2]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[0]));
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(), "Joana", "Joana", birthOfHolder, address, accountHolderDTO.getEmail());

        return  accountHolderRepository.save(accountHolder);
    }
}
