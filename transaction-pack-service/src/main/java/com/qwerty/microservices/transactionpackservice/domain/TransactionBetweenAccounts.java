package com.qwerty.microservices.transactionpackservice.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
public class TransactionBetweenAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int accountNumberFrom;
    private int accountNumberTo;
    private int transactionNumberBA;
    private BigDecimal accountBalanceFrom;
    private BigDecimal accountBalanceTo;
    private Date date;
    private String description;
    private String type;
    private String status;
    private BigDecimal amount;
    private UUID uuid = UUID.randomUUID();

    public TransactionBetweenAccounts() {
    }

    public TransactionBetweenAccounts(int accountNumberFrom, int accountNumberTo, int transactionNumberBA, BigDecimal accountBalanceFrom, BigDecimal accountBalanceTo, String description, BigDecimal amount, UUID uuid) {
        this.accountNumberFrom = accountNumberFrom;
        this.accountNumberTo = accountNumberTo;
        this.transactionNumberBA = transactionNumberBA;
        this.accountBalanceFrom = accountBalanceFrom;
        this.accountBalanceTo = accountBalanceTo;
        this.description = description;
        this.amount = amount;
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAccountNumberFrom() {
        return accountNumberFrom;
    }

    public void setAccountNumberFrom(int accountNumberFrom) {
        this.accountNumberFrom = accountNumberFrom;
    }

    public int getAccountNumberTo() {
        return accountNumberTo;
    }

    public void setAccountNumberTo(int accountNumberTo) {
        this.accountNumberTo = accountNumberTo;
    }

    public int getTransactionNumberBA() {
        return transactionNumberBA;
    }

    public void setTransactionNumberBA(int transactionNumberBA) {
        this.transactionNumberBA = transactionNumberBA;
    }

    public BigDecimal getAccountBalanceFrom() {
        return accountBalanceFrom;
    }

    public void setAccountBalanceFrom(BigDecimal accountBalanceFrom) {
        this.accountBalanceFrom = accountBalanceFrom;
    }

    public BigDecimal getAccountBalanceTo() {
        return accountBalanceTo;
    }

    public void setAccountBalanceTo(BigDecimal accountBalanceTo) {
        this.accountBalanceTo = accountBalanceTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
