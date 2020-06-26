package com.ironhack.bankSystem.controller.interfaces;

import com.ironhack.bankSystem.dto.AccountHolderPostDTO;
import com.ironhack.bankSystem.model.AccountHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface AccountHolderInterface {
    AccountHolder getAccountHolderById(Long id);
    List<AccountHolder> getAccountHolders();
    AccountHolder getAccountHolderById(AccountHolderPostDTO accountHolderDTO);
}
