package com.qwerty.microservices.transactionpackservice.service;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static int nextTransactionNumber = 1;
    private TransactionDao transactionDao;


    @Autowired
    public void setTransactionDao(TransactionDao transactionDao){
        this.transactionDao = transactionDao;
    }

    public TransactionDao getTransactionDao(){
        return transactionDao;
    }


    @Override
    public Transaction createTransaction(int accountNumber, BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount) {


        if (transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber)==null){
            Transaction transaction = new Transaction(0,accountNumber,new BigDecimal(0),transactionType,transactionAmount);
            transactionDao.save(transaction);
        }
        Transaction controlledTransaction = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber);

        int updatedTransactionNumber = controlledTransaction.getTransactionNumber()+1;

        Transaction transaction =  new Transaction(updatedTransactionNumber,accountNumber
                ,controlledTransaction.getAccountBalance(),transactionType,transactionAmount);

            transactionDao.save(transaction);

        return transaction;
        }


    @Override
    public Transaction deposit(int transactionNumber, int accountNumber, BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount) {

        Transaction transaction = createTransaction(accountNumber,accountBallance,transactionType,transactionAmount);
        transaction.setAccountBalance(accountBallance.add(transactionAmount));
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction withDraw(int transactionNumber, int accountNumber, BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount) {
        Transaction transaction = createTransaction(accountNumber,accountBallance,transactionType,transactionAmount.multiply(BigDecimal.valueOf(-1)));
        transaction.setAccountBalance(accountBallance.subtract(transactionAmount));
        return transaction;
    }



    @Override
    public boolean CheckAccountBalance(int accountNumber, BigDecimal accountBallance, BigDecimal amount) {
        int res = accountBallance.compareTo(amount);
        return res >= 0;
    }

    private int transactionGen() {
        return ++nextTransactionNumber;
    }
}