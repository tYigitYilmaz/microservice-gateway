package com.qwerty.microservice.transactionservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class TransactionBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int transactionNumber;
    private BigDecimal accountBalance;


    public TransactionBalance(int transactionNumber, BigDecimal accountBalance) {
        this.transactionNumber = transactionNumber;
        this.accountBalance = accountBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
}
