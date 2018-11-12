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
    public Transaction createTransaction(int transactionNumber, int accountNumber, BigDecimal amount, BigDecimal availableBalance) {
       Transaction transaction = new Transaction(transactionGen(),accountNumber,amount,availableBalance);
        return null;
    }

    @Override
    public Transaction deposit(int accountNumber, int transactionId, BigDecimal accountBalance, String amount) {
        Transaction transaction = createTransaction(transactionGen(),accountNumber,new BigDecimal(amount),accountBalance);
        transaction.setAvailableBalance(transaction.getAvailableBalance().add(new BigDecimal(amount)));
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction withDraw(int accountNumber, int transactionId, BigDecimal accountBalance, String amount) {
        Transaction transaction = createTransaction(transactionGen(),accountNumber,new BigDecimal(amount).multiply(BigDecimal.valueOf(-1)),accountBalance);
        transaction.setAvailableBalance(transaction.getAvailableBalance().subtract(new BigDecimal(amount)));
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction transactionBetweenAccounts(int accountFrom, int accountTo, Long transactionId, BigDecimal accountFromBalance, BigDecimal accountToBalance, String amount) {

        Transaction transactionFrom = createTransaction(transactionGen(),accountFrom,new BigDecimal(amount).multiply(BigDecimal.valueOf(-1)),accountFromBalance);
        Transaction transactionTo = createTransaction(transactionGen(),accountTo,new BigDecimal(amount),accountFromBalance);
        transactionFrom.setAvailableBalance(transactionFrom.getAvailableBalance().subtract(new BigDecimal(amount)));
        transactionTo.setAvailableBalance(transactionTo.getAvailableBalance().add(new BigDecimal(amount)));
        transactionDao.save(transactionFrom);
        transactionDao.save(transactionTo);
        return transactionFrom;
    }

    @Override
    public boolean CheckAccountBalance(int accountNumber, BigDecimal accountBallance, String amount) {
        int res = accountBallance.compareTo(new BigDecimal(amount));
        return res >= 0;
    }

    private int transactionGen() {
        return ++nextTransactionNumber;
    }
}
