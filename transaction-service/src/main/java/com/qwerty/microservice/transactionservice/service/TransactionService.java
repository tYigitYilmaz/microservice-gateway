package com.qwerty.microservice.transactionservice.service;

import com.qwerty.microservice.transactionservice.domain.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface TransactionService {

    Transaction createTransaction(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalance);

    Transaction deposit(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalance);

    Transaction withDraw(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalancet);

    Transaction transactionBetweenAccounts(int accountFrom,int accountTo,String description
            ,int transactionNumber,BigDecimal accountFromBalance,BigDecimal accountToBalance,BigDecimal amount);

    boolean CheckAccountBalance(int accountNumber, BigDecimal accountBallance, BigDecimal amount);

}
