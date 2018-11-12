package com.qwerty.microservices.accountservice.service.serviceImpl;


import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.TransactionNumber;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.domain.repository.TransactionNumberDao;
import com.qwerty.microservices.accountservice.service.TransactionNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionNumberServiceImpl implements TransactionNumberService {
    private AccountDao accountDao;
    private TransactionNumberDao transactionNumberDao;

    @Autowired
    public void SetAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Autowired
    public void SetTransactionNumberDao(TransactionNumberDao transactionNumberDao) {
        this.transactionNumberDao = transactionNumberDao;
    }

    public TransactionNumberDao getTransactionNumberDao() {
        return transactionNumberDao;
    }


    @Override
    public boolean addTransactionNumber(int transactionNumber, int accountNumber) {

        TransactionNumber transaction = new TransactionNumber(transactionNumber,accountNumber);
        transactionNumberDao.save(transaction);
        return true;
    }
}
