package com.ironhack.bankSystem.controller.implementation;

import com.ironhack.bankSystem.controller.interfaces.AccountHolderInterface;
import com.ironhack.bankSystem.dto.AccountHolderPostDTO;
import com.ironhack.bankSystem.model.AccountHolder;
import com.ironhack.bankSystem.service.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class AccountHolderImplementation implements AccountHolderInterface {
    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/accountHolder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolderById(@PathVariable Long id){
        return null;
    }

    @PostMapping("/accountHolders")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AccountHolder> getAccountHolders(){
        return null;
    }

    @PostMapping("/accountHolder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder getAccountHolderById(@RequestBody AccountHolderPostDTO accountHolderDTO){
        return null;
    }
}
