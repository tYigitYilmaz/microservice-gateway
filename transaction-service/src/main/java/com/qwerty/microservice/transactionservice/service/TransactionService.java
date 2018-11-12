package com.qwerty.microservice.transactionservice.service;

import com.qwerty.microservice.transactionservice.domain.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface TransactionService {

    Transaction createTransaction(int transactionNumber,int accountNumber,BigDecimal amount,BigDecimal availableBalance);

    Transaction deposit(int accountNumber, int transactionNumber, BigDecimal accountBalance, String amount);

    Transaction withDraw(int accountNumber,int transactionNumber,BigDecimal accountBalance,String amount);

    Transaction transactionBetweenAccounts(int accountFrom,int accountTo
            ,Long transactionId,BigDecimal accountFromBalance,BigDecimal accountToBalance,String amount);

    boolean CheckAccountBalance(int accountNumber, BigDecimal accountBallance, String amount);

}
