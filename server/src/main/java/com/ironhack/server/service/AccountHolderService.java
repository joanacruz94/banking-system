package com.ironhack.server.service;

import com.ironhack.server.dto.AccountHolderPostDTO;
import com.ironhack.server.exceptions.NotFoundException;
import com.ironhack.server.model.AccountHolder;
import com.ironhack.server.model.Address;
import com.ironhack.server.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountHolderService {
    @Autowired
    AccountHolderRepository accountHolderRepository;

    public AccountHolder createAccountHolder(AccountHolderPostDTO accountHolderDTO){
        Address address = new Address(accountHolderDTO.getCity(), accountHolderDTO.getCountry(), accountHolderDTO.getStreetAddress(), accountHolderDTO.getHouseNumber(), accountHolderDTO.getZipCode());
        String[] dateOfBirth = accountHolderDTO.getDateOfBirth().split("-");
        LocalDate birthOfHolder = LocalDate.of(Integer.parseInt(dateOfBirth[2]), Integer.parseInt(dateOfBirth[1]), Integer.parseInt(dateOfBirth[0]));
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(), "Joana", "Joana", birthOfHolder, address);

        return accountHolderRepository.save(accountHolder);
    }

    public AccountHolder findAccountHolderById(Long id){
        return accountHolderRepository.findById(id).orElseThrow(() -> new NotFoundException("Account holder with that ID doesn't exist"));
    }

    public List<AccountHolder> findAllAccountHolders(){
        return accountHolderRepository.findAll();
    }
}
