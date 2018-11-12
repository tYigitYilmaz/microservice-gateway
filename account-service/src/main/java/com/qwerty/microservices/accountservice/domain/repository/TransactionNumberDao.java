package com.qwerty.microservices.accountservice.domain.repository;

import com.qwerty.microservices.accountservice.domain.TransactionNumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionNumberDao extends CrudRepository<TransactionNumber,Long> {

    TransactionNumber findByTransactionNumber(int transactionNumber);
    TransactionNumber findByAccountNumber(int accountNumber);
}
