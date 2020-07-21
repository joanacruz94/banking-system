package com.ironhack.server.controller.implementation;

import com.ironhack.server.controller.interfaces.TransactionInterface;
import com.ironhack.server.dto.AccountGetDTO;
import com.ironhack.server.dto.TransactionGetDTO;
import com.ironhack.server.dto.TransactionPostDTO;
import com.ironhack.server.model.Transaction;
import com.ironhack.server.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController implements TransactionInterface {
    @Autowired
    TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "See information of all transactions in the system.")
    @GetMapping("/transactions")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionGetDTO> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "See information of all transactions of an account in the system." +
            "You can filter by: sent | received")
    @GetMapping("/transactionsAccount/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionGetDTO> getAllTransactionsAccount(@PathVariable Long id, @RequestParam(name="filter", required = false, defaultValue = "") String filter){
        return transactionService.getTransactionsAccount(id, filter);
    }


    @PreAuthorize("hasRole('ACCOUNTHOLDER')")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionGetDTO executeTransaction(@RequestBody TransactionPostDTO transactionDTO){
        return transactionService.transaction(transactionDTO);
    }

}
