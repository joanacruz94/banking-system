package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.AccountHolderDTO;
import com.ironhack.server.dto.SignUpAccountHolderRequest;
import com.ironhack.server.model.AccountHolder;

import java.util.List;

public interface AccountHolderInterface {
    AccountHolderDTO getAccountHolderById(Long id);
    List<AccountHolderDTO> getAccountHolders();
    Long createAccountHolder(SignUpAccountHolderRequest accountHolderDTO);
}
