package com.server.controller.implementation;

import com.server.controller.interfaces.AccountHolderInterface;
import com.server.dto.AccountHolderDTO;
import com.server.dto.SignUpAccountHolderRequest;
import com.server.service.AccountHolderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountHolder")
public class AccountHolderController implements AccountHolderInterface {
    @Autowired
    AccountHolderService accountHolderService;

    @GetMapping("/{id}")
    @ApiOperation(value = "See information of an account holder user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolderDTO getAccountHolderById(@PathVariable Long id){
        return accountHolderService.findAccountHolderById(id);
    }

    @ApiOperation(value = "Check all account holders in the system")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/accountHolders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolderDTO> getAccountHolders(){
        return accountHolderService.findAllAccountHolders();
    }

    @ApiOperation(value = "Insert a new account holder user. Method returns the ID generated for the user.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createAccountHolder(@RequestBody SignUpAccountHolderRequest accountHolderDTO){
        return accountHolderService.registerAccountHolder(accountHolderDTO);
    }
}
