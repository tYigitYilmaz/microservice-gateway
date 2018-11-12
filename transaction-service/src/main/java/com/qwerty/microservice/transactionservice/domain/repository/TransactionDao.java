package com.qwerty.microservice.transactionservice.domain.repository;

import com.qwerty.microservice.transactionservice.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionDao extends CrudRepository<Transaction,Long> {

    Transaction findByTransactionNumber(int transactionNumber);
}
