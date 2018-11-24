package com.qwerty.microservices.transactionpackservice.domain.repository;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionBetweenAccounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionBetweenAccDao extends CrudRepository<TransactionBetweenAccounts,Long> {

        TransactionBetweenAccounts findByAccountNumberFrom(int accountNumberFrom);

        TransactionBetweenAccounts findByAccountNumberTo(int accountNumberTo);

        TransactionBetweenAccounts findFirstByAccountNumberFromOrderByTransactionNumberBADesc(int accountNumberFrom);

        TransactionBetweenAccounts findFirstByAccountNumberToOrderByTransactionNumberBADesc(int accountNumberTo);
}
