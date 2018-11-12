package com.qwerty.microservices.accountservice.domain.repository;

import com.qwerty.microservices.accountservice.domain.TransactionNumber;
import org.springframework.data.repository.CrudRepository;

public interface TransactionNumberDao extends CrudRepository<TransactionNumber,Long> {

    TransactionNumber findByTransactionNumber(int transactionNumber);
}
