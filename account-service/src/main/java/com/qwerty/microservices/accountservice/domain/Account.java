package com.qwerty.microservices.accountservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name="seq", initialValue=1000000, allocationSize=10000000)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int accountNumber;
    private BigDecimal accountBalance;


    private ArrayList<TransactionNumber> transactioNumberList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public ArrayList<TransactionNumber> getTransactioNumberList() {
        return transactioNumberList;
    }

    public void setTransactioNumberList(ArrayList<TransactionNumber> transactioNumberList) {
        this.transactioNumberList = transactioNumberList;
    }
}