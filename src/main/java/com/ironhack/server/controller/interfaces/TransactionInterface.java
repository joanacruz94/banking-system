package com.ironhack.server.controller.interfaces;

import com.ironhack.server.dto.TransactionGetDTO;
import com.ironhack.server.dto.TransactionPostDTO;

import java.util.List;

public interface TransactionInterface {
    List<TransactionGetDTO> getAllTransactions();
    List<TransactionGetDTO> getAllTransactionsAccount(Long id, String filter);
    TransactionGetDTO executeTransaction(TransactionPostDTO transactionDTO);
}
