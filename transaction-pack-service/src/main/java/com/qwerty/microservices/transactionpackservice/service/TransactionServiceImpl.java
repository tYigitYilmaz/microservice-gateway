package com.qwerty.microservices.transactionpackservice.service;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transactionDao;


    @Autowired
    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }


    @Override
    public Transaction createTransaction(int accountNumber, BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount) {


        if (transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber) == null) {
            Transaction transaction = new Transaction(0, accountNumber, new BigDecimal(0), transactionType, transactionAmount);
            transactionDao.save(transaction);
        }
        Transaction controlledTransaction = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber);

        int updatedTransactionNumber = controlledTransaction.getTransactionNumber() + 1;
        Transaction transaction = new Transaction(updatedTransactionNumber, accountNumber
                , controlledTransaction.getAccountBalance(), transactionType, transactionAmount);
        transactionDao.save(transaction);

        return transaction;
    }


    @Override
    public Transaction deposit(int transactionNumber, int accountNumber, BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount) {

        Transaction transaction = createTransaction(accountNumber, accountBallance, transactionType, transactionAmount);
        transaction.setAccountBalance(accountBallance.add(transactionAmount));
        transactionDao.save(transaction);

        return transaction;
    }

    @Override
    public Transaction withDraw(int transactionNumber, int accountNumber, BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount) {
        Transaction transaction = createTransaction(accountNumber, accountBallance, transactionType, transactionAmount.multiply(BigDecimal.valueOf(-1)));
        checkAccountBalance(accountNumber, accountBallance, transactionAmount);
        transaction.setAccountBalance(accountBallance.subtract(transactionAmount));
        transactionDao.save(transaction);

        return transaction;
    }


    @Override
    public Transaction betweenAccounts(int transactionNumber, int accountNumberFrom, int accountNumberTo, BigDecimal accountBallanceFrom
            , BigDecimal accountBalanceTo, String transactionType, BigDecimal transactionAmount) {

        Transaction transactionFrom = createTransaction(accountNumberFrom, accountBallanceFrom, transactionType, transactionAmount);
        checkAccountBalance(transactionFrom.getAccountNumber(), transactionFrom.getAccountBalance(), transactionAmount);
        withDraw(transactionNumber, transactionFrom.getAccountNumber(), transactionFrom.getAccountBalance()
                , transactionType, transactionAmount);

        Transaction transactionTo = createTransaction(accountNumberTo, accountBalanceTo, transactionType, transactionAmount);
        deposit(transactionNumber, transactionTo.getAccountNumber(), transactionTo.getAccountBalance()
                , transactionType, transactionAmount);

        transactionDao.save(transactionFrom);
        transactionDao.save(transactionTo);

        return transactionFrom;
    }

    @Override
    public boolean checkAccountBalance(int accountNumber, BigDecimal accountBallance, BigDecimal amount) {
        int res = accountBallance.compareTo(amount);
        return res >= 0;
    }
}