package com.qwerty.microservice.transactionservice.domain.repository;


import com.qwerty.microservice.transactionservice.domain.TransactionBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionBalanceDao extends CrudRepository<TransactionBalance,Long> {
    TransactionBalance findByTransactionNumber(int transactionNumber);
}
