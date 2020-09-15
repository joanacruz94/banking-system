package com.server.controller.interfaces;

import com.server.dto.TransactionGetDTO;
import com.server.dto.TransactionPostDTO;

import java.util.List;

public interface TransactionInterface {
    List<TransactionGetDTO> getAllTransactions();
    List<TransactionGetDTO> getAllTransactionsAccount(Long id, String filter);
    TransactionGetDTO executeTransaction(TransactionPostDTO transactionDTO);
}
