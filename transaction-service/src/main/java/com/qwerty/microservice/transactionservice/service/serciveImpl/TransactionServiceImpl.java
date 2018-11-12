package com.qwerty.microservice.transactionservice.service.serciveImpl;

import com.qwerty.microservice.transactionservice.domain.Transaction;
import com.qwerty.microservice.transactionservice.domain.repository.TransactionDao;
import com.qwerty.microservice.transactionservice.service.TransactionService;
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
    public Transaction createTransaction(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalance) {
       Transaction transaction = new Transaction(transactionNumber,description,accountNumber,amount,availableBalance);
       transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction deposit(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalance) {
        Transaction transaction = createTransaction(transactionNumber,description,accountNumber,amount,availableBalance);
        transaction.setAccountBalance(transaction.getAccountBalance().add(amount));
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction withDraw(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalance) {
        Transaction transaction = createTransaction(transactionGen(),description,accountNumber,amount.multiply(BigDecimal.valueOf(-1)),availableBalance);
        transaction.setAccountBalance(transaction.getAccountBalance().subtract(amount));
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction transactionBetweenAccounts(int accountFrom,int accountTo,String description
            ,int transactionNumber,BigDecimal accountFromBalance,BigDecimal accountToBalance,BigDecimal amount) {

        Transaction transactionFrom = createTransaction(transactionNumber,description,accountFrom,amount.multiply(BigDecimal.valueOf(-1)),accountFromBalance);
        Transaction transactionTo = createTransaction(transactionNumber,description,accountTo,amount,accountFromBalance);
        transactionFrom.setAccountBalance(transactionFrom.getAccountBalance().subtract(amount));
        transactionTo.setAccountBalance(transactionTo.getAccountBalance().add(amount));
        transactionDao.save(transactionFrom);
        transactionDao.save(transactionTo);
        return transactionFrom;
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
