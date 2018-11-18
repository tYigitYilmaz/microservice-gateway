package com.qwerty.microservices.transactionpackservice.service;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface TransactionService {

    Transaction createTransaction(int accountNumber, BigDecimal accountBallance,
                                  String transactionType, BigDecimal transactionAmount);

    Transaction deposit(int transactionNumber, int accountNumber, BigDecimal accountBallance, String transactionType
            , BigDecimal transactionAmount);

    Transaction withDraw(int transactionNumber, int accountNumber,
                         BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount);

    boolean betweenAccounts(int transactionNumberFrom,int transactionNumberTo, int accountNumberFrom,int accountNumberTo, BigDecimal accountBallanceFrom
                                ,BigDecimal accountBalanceTo, String transactionType, BigDecimal transactionAmount);

    boolean checkAccountBalance(int accountNumber, BigDecimal accountBallance, BigDecimal amount);

}