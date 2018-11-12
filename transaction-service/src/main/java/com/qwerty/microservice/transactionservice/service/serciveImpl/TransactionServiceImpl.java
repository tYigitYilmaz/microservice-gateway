package com.qwerty.microservice.transactionservice.service.serciveImpl;

import com.qwerty.microservice.transactionservice.domain.Transaction;
import com.qwerty.microservice.transactionservice.domain.TransactionBalance;
import com.qwerty.microservice.transactionservice.domain.repository.TransactionBalanceDao;
import com.qwerty.microservice.transactionservice.domain.repository.TransactionDao;
import com.qwerty.microservice.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static int nextTransactionNumber = 1;
    private TransactionDao transactionDao;
    private TransactionBalanceDao transactionBalanceDao;

    @Autowired
   public void setTransactionDao(TransactionDao transactionDao){
       this.transactionDao = transactionDao;
   }

   public TransactionDao getTransactionDao(){
       return transactionDao;
   }

    @Autowired
    public void setTransactionBalanceDao(TransactionBalanceDao transactionBalanceDao){
        this.transactionBalanceDao = transactionBalanceDao;
    }

    public TransactionBalanceDao getTransactionBalanceDao(){
        return transactionBalanceDao;
    }

    @Override
    public Transaction createTransaction(int transactionNumber,String description, int accountNumber, BigDecimal amount) {
       Transaction transaction = new Transaction(transactionNumber,description,accountNumber,amount);
       transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction deposit(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalance) {
        Transaction transaction = createTransaction(transactionNumber,description,accountNumber,amount);
        TransactionBalance transactionBalance = new TransactionBalance(transactionNumber,availableBalance);
        transactionBalance.setAccountBalance(transactionBalance.getAccountBalance().add(amount));
        transactionBalanceDao.save(transactionBalance);
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction withDraw(int transactionNumber,String description, int accountNumber, BigDecimal amount, BigDecimal availableBalance) {
        Transaction transaction = createTransaction(transactionGen(),description,accountNumber,amount.multiply(BigDecimal.valueOf(-1)));
        TransactionBalance transactionBalance = new TransactionBalance(transactionNumber,availableBalance);
        transactionBalance.setAccountBalance(transactionBalance.getAccountBalance().subtract(amount));
        transactionBalanceDao.save(transactionBalance);
        transactionDao.save(transaction);
        return transaction;
    }

    @Override
    public Transaction transactionBetweenAccounts(int accountFrom,int accountTo,String description
            ,int transactionNumber,BigDecimal accountFromBalance,BigDecimal accountToBalance,BigDecimal amount) {

        Transaction transactionFrom = createTransaction(transactionNumber,description,accountFrom,amount.multiply(BigDecimal.valueOf(-1)));
        Transaction transactionTo = createTransaction(transactionNumber,description,accountTo,amount);
        TransactionBalance transactionFromBalance = new TransactionBalance(transactionNumber,accountFromBalance);
        TransactionBalance transactionToBalance = new TransactionBalance(transactionNumber,accountToBalance);

        transactionFromBalance.setAccountBalance(transactionFromBalance.getAccountBalance().subtract(amount));
        transactionToBalance.setAccountBalance(transactionToBalance.getAccountBalance().add(amount));
        transactionBalanceDao.save(transactionFromBalance);
        transactionBalanceDao.save(transactionToBalance);

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
