package com.qwerty.microservices.accountservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private int accountNumber;

    @Column(name = "Acc_RUB")
    private BigDecimal accountBalance;

    @Column(name = "Acc_USD")
    private BigDecimal accountBalanceUsd;

    @Column(name = "Acc_EUR")
    private BigDecimal accountBalanceEur;

    private UUID uuid = UUID.randomUUID();

    @JsonIgnore
    private int port;
    private BigDecimal conversionMultiply;
    private BigDecimal conversionAmount;

    public Account(int accountNumber, BigDecimal accountBalance, BigDecimal accountBalanceUsd, BigDecimal accountBalanceEur) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountBalanceUsd = accountBalanceUsd;
        this.accountBalanceEur = accountBalanceEur;
    }

    public Account(){
    }

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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getConversionMultiply() {
        return conversionMultiply;
    }

    public void setConversionMultiply(BigDecimal conversionMultiply) {
        this.conversionMultiply = conversionMultiply;
    }

    public BigDecimal getAccountBalanceUsd() {
        return accountBalanceUsd;
    }

    public void setAccountBalanceUsd(BigDecimal accountBalanceUsd) {
        this.accountBalanceUsd = accountBalanceUsd;
    }

    public BigDecimal getAccountBalanceEur() {
        return accountBalanceEur;
    }

    public void setAccountBalanceEur(BigDecimal accountBalanceEur) {
        this.accountBalanceEur = accountBalanceEur;
    }

    public BigDecimal getConversionAmount() {
        return conversionAmount;
    }

    public void setConversionAmount(BigDecimal conversionAmount) {
        this.conversionAmount = conversionAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", accountBalance=" + accountBalance +
                ", accountBalanceUsd=" + accountBalanceUsd +
                ", uuid=" + uuid +
                '}';
    }
}
