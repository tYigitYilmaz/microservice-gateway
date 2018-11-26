package com.qwerty.microservices.transactionpackservice.service;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionBetweenAccounts;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface TransactionService {

    Transaction createTransaction(int accountNumber,BigDecimal transactionAmount);

    Transaction deposit(Transaction transaction);

    Transaction withDraw(Transaction transaction);

    TransactionBetweenAccounts createTransactionBA(int accountNumberFrom, int accountNumberTo,BigDecimal transactionAmount);

    TransactionBetweenAccounts betweenAccounts(TransactionBetweenAccounts transactionBetweenAccounts);

    boolean checkAccountBalance(BigDecimal accountBalance, BigDecimal amount);

}