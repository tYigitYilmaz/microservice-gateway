package com.qwerty.microservices.transactionpackservice.service;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionBetweenAccounts;
import com.qwerty.microservices.transactionpackservice.domain.repository.TransactionBetweenAccDao;
import com.qwerty.microservices.transactionpackservice.domain.repository.TransactionDao;
import com.qwerty.microservices.transactionpackservice.web.AccountProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transactionDao;
    private TransactionBetweenAccDao transactionBetweenAccDao;
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

    @Autowired
    private void SetTransactionBetweenAccDao(TransactionBetweenAccDao transactionBetweenAccDao){
        this.transactionBetweenAccDao = transactionBetweenAccDao;
    }


    private TransactionBetweenAccDao getTransactionBetweenAccDao(){
        return transactionBetweenAccDao;
    }



    @Override
    public Transaction createTransaction(int accountNumber, BigDecimal transactionAmount) {


        if (transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber) == null) {
            Transaction transaction = new Transaction(1, accountNumber, new BigDecimal(0), "withdraw", transactionAmount);
            transactionDao.save(transaction);
        }
        Transaction controlledTransaction = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber);

        int updatedTransactionNumber = controlledTransaction.getTransactionNumber() + 1;
        Transaction transaction = new Transaction(updatedTransactionNumber, accountNumber
                , controlledTransaction.getAccountBalance(), "withdraw", transactionAmount);
        transactionDao.save(transaction);

        return transaction;
    }

    @Override
    public TransactionBetweenAccounts createTransactionBA(int accountNumberFrom,int accountNumberTo,
                                       BigDecimal transactionAmount) {
        if (transactionBetweenAccDao.findFirstByAccountNumberFromOrderByTransactionNumberBADesc(accountNumberFrom) == null) {
            TransactionBetweenAccounts transactionBetweenAccounts = new
                    TransactionBetweenAccounts(accountNumberFrom,accountNumberTo,1 ,
                    transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumberFrom).getAccountBalance(),
                    transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumberTo).getAccountBalance()
                    ,"BetweenAccounts", transactionAmount);
            transactionBetweenAccDao.save(transactionBetweenAccounts);
            return transactionBetweenAccounts;
        } else {
            TransactionBetweenAccounts controlledTransactionBA =
                    transactionBetweenAccDao.findFirstByAccountNumberFromOrderByTransactionNumberBADesc(accountNumberFrom);

            int updatedTransactionNumber = controlledTransactionBA.getTransactionNumberBA() + 1;

            TransactionBetweenAccounts transactionBetweenAccounts = new TransactionBetweenAccounts(accountNumberFrom,accountNumberTo
                    ,updatedTransactionNumber,controlledTransactionBA.getAccountBalanceFrom(),controlledTransactionBA.getAccountBalanceTo()
                    ,"BetweenAccounts", transactionAmount);
            transactionBetweenAccDao.save(transactionBetweenAccounts);

            return transactionBetweenAccounts;
        }
    }

    @Override
    public Transaction deposit(int accountNumber, BigDecimal transactionAmount) {

        Transaction transaction = createTransaction(accountNumber, transactionAmount);
        Transaction updatedResponse = accountProxy.accountMatcher(transaction);
        transaction.setAccountBalance(updatedResponse.getAccountBalance().add(transactionAmount));
        transaction.setDescription("deposit");
        transactionDao.save(transaction);
        return transaction;
    }


    @Override
    public Transaction withDraw(int accountNumber, BigDecimal transactionAmount) {
        Transaction transaction = createTransaction(accountNumber, transactionAmount.multiply(BigDecimal.valueOf(-1)));
        Transaction updatedResponse = accountProxy.accountMatcher(transaction);
        if (checkAccountBalance(updatedResponse.getAccountBalance(), transactionAmount)){
            transaction.setAccountBalance(updatedResponse.getAccountBalance().subtract(transactionAmount));
            transaction.setDescription("withdraw");
            transactionDao.save(transaction);
            return transaction;
        }
        return transaction;
    }


    @Override
    public TransactionBetweenAccounts betweenAccounts(int accountNumberFrom, int accountNumberTo,BigDecimal transactionAmount) {

        withDraw(accountNumberFrom,transactionAmount);
        deposit(accountNumberTo,transactionAmount);
        TransactionBetweenAccounts transactionBetweenAccounts = createTransactionBA(accountNumberFrom,accountNumberTo,transactionAmount);
        transactionBetweenAccounts.setDescription("BetweenAccounts");
        transactionBetweenAccDao.save(transactionBetweenAccounts);
        return  transactionBetweenAccounts;
    }

    @Override
    public boolean checkAccountBalance(BigDecimal accountBallance, BigDecimal amount) {
        int res = accountBallance.compareTo(amount);
        return res >= 0;
    }
}