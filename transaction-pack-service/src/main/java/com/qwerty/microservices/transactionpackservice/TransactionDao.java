package com.qwerty.microservices.transactionpackservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends CrudRepository<Transaction,Long> {

    Transaction findByTransactionNumber(int transactionNumber);
}