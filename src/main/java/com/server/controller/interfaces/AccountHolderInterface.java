package com.server.controller.interfaces;

import com.server.dto.AccountHolderDTO;
import com.server.dto.SignUpAccountHolderRequest;

import java.util.List;

public interface AccountHolderInterface {
    AccountHolderDTO getAccountHolderById(Long id);
    List<AccountHolderDTO> getAccountHolders();
    Long createAccountHolder(SignUpAccountHolderRequest accountHolderDTO);
}
