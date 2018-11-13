package com.qwerty.microservices.transactionpackservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBalanceDao extends CrudRepository<TransactionBalance,Long> {
    TransactionBalance findByTransactionNumber(int transactionNumber);
}