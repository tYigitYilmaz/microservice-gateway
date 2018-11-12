package com.qwerty.microservices.accountservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int transactionNumber;
    private int accountNumber;

    @JsonIgnore
    private int port;

    public TransactionNumber() {
    }

    public TransactionNumber(int transactionNumber, int accountNumber) {
        this.transactionNumber = transactionNumber;
        this.accountNumber = accountNumber;
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

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
