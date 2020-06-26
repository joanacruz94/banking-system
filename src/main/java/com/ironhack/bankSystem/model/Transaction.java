package com.ironhack.bankSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_account_from", nullable=false)
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name="id_account_to", nullable=false)
    private Account accountTo;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime transactionDate;

    public Transaction(Account accountFrom, Account accountTo, BigDecimal amount){
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

}
