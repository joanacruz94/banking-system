package com.ironhack.server.controller.implementation;

import com.ironhack.server.controller.interfaces.AccountHolderInterface;
import com.ironhack.server.dto.AccountHolderPostDTO;
import com.ironhack.server.model.AccountHolder;
import com.ironhack.server.service.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountHolder")
public class AccountHolderController implements AccountHolderInterface {
    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getAccountHolderById(@PathVariable Long id){
        return accountHolderService.findAccountHolderById(id);
    }

    @PostMapping("/accountHolders")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AccountHolder> getAccountHolders(){
        return accountHolderService.findAllAccountHolders();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder getAccountHolderById(@RequestBody AccountHolderPostDTO accountHolderDTO){
        return accountHolderService.createAccountHolder(accountHolderDTO);
    }
}
