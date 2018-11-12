package com.qwerty.microservices.accountservice.service;

import com.qwerty.microservices.accountservice.domain.TransactionNumber;

public interface TransactionNumberService {

    boolean addTransactionNumber(int transactionNumber,int accountNumber);
}
