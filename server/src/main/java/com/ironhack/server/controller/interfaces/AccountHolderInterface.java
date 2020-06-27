package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.AccountHolderPostDTO;
import com.ironhack.server.model.AccountHolder;

import java.util.List;

public interface AccountHolderInterface {
    AccountHolder getAccountHolderById(Long id);
    List<AccountHolder> getAccountHolders();
    AccountHolder getAccountHolderById(AccountHolderPostDTO accountHolderDTO);
}
