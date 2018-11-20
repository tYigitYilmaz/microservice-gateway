package com.qwerty.microservices.transactionpackservice.service;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionDao;
import com.qwerty.microservices.transactionpackservice.web.AccountProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transactionDao;
    private AccountProxy accountProxy;

    @Autowired
    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }
    @Autowired
    private void SetAccountProxy(AccountProxy accountProxy){
        this.accountProxy = accountProxy;
    }


    private AccountProxy getAccountProxy(){
        return accountProxy;
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
    public Transaction deposit(int transactionNumber, int accountNumber, BigDecimal accountBalance, String transactionType, BigDecimal transactionAmount) {

        Transaction transaction = createTransaction(accountNumber, accountBalance, "deposit", transactionAmount);
        Transaction updatedResponse = accountProxy.accountMatcher(transaction);
        transaction.setAccountBalance(updatedResponse.getAccountBalance().add(transactionAmount));
        transactionDao.save(transaction);
        return transaction;
    }


    @Override
    public Transaction withDraw(int transactionNumber, int accountNumber, BigDecimal accountBallance, String transactionType, BigDecimal transactionAmount) {
        Transaction transaction = createTransaction(accountNumber, accountBallance, transactionType, transactionAmount.multiply(BigDecimal.valueOf(-1)));
        Transaction updatedResponse = accountProxy.accountMatcher(transaction);
        checkAccountBalance(updatedResponse.getAccountNumber(), updatedResponse.getAccountBalance(), transactionAmount);
        transaction.setAccountBalance(updatedResponse.getAccountBalance().subtract(transactionAmount));
        transactionDao.save(transaction);

        return transaction;
    }


    @Override
    public boolean betweenAccounts(int transactionNumberFrom,int transactionNumberTo, int accountNumberFrom, int accountNumberTo, BigDecimal accountBallanceFrom
            , BigDecimal accountBalanceTo, String transactionType, BigDecimal transactionAmount) {

        withDraw(transactionNumberFrom,accountNumberFrom,accountBallanceFrom,transactionType,transactionAmount);
        deposit(transactionNumberTo,accountNumberTo,accountBalanceTo,transactionType,transactionAmount);

        return true;
    }

    @Override
    public boolean checkAccountBalance(int accountNumber, BigDecimal accountBallance, BigDecimal amount) {
        int res = accountBallance.compareTo(amount);
        return res >= 0;
    }
}