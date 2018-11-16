package com.qwerty.microservices.transactionpackservice.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends CrudRepository<Transaction,Long> {

    Transaction findByAccountNumber(int accountNumber);
    Transaction findByTransactionNumber(int transactionNumber);
    Transaction deleteByTransactionNumber(int transactionNumber);
    Transaction findFirstByAccountNumberOrderByTransactionNumberDesc(int accountNumber);
}