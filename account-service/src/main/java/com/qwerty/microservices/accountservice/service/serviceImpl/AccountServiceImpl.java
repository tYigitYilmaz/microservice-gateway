package com.qwerty.microservices.accountservice.service.serviceImpl;


import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.service.AccountService;
import com.qwerty.microservices.accountservice.web.AccountCurrencyProxy;
import com.qwerty.microservices.accountservice.web.TransactionProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;
    private AccountCurrencyProxy accountCurrencyProxy;
    private TransactionProxy transactionProxy;

    @Autowired
    public void SetAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Autowired
    public void setTransactionProxy(TransactionProxy transactionProxy) {
        this.transactionProxy = transactionProxy;
    }

    public TransactionProxy getTransactionProxy() {
        return transactionProxy;
    }

    @Autowired
    public void setAccountCurrencyProxy(AccountCurrencyProxy accountCurrencyProxy) {
        this.accountCurrencyProxy = accountCurrencyProxy;
    }

    public AccountCurrencyProxy getAccountCurrencyProxy() {
        return accountCurrencyProxy;
    }

    @Override
    public BigDecimal findAccountBalance(int accountNumber) {
        Account account = accountDao.findByAccountNumber(accountNumber);
        return account.getAccountBalance();
    }
    @Override
    public Account transactionAccountUpdate(int accountNumber) {
       Account account = accountDao.findByAccountNumber(accountNumber);
       Account responseConfig = transactionProxy.transactionConfirm(account);
       account.setAccountBalance(responseConfig.getAccountBalance());
       accountDao.save(account);
       return account;
    }

    @Override
    public Account accountCurrencyExchangeUpdate(Account account,String from,String to) {
        Account accountImp = accountDao.findByAccountNumber(account.getAccountNumber());
        Account responseConfig = accountCurrencyProxy.CurrencyConfirm(from,to);
        if (to.equals("USD")) {
            accountImp.setAccountBalanceUsd(accountImp.getAccountBalanceUsd()
                    .add(responseConfig.getConversionMultiply()
                            .multiply(account.getConversionAmount())));

        accountImp.setAccountBalance(accountImp.getAccountBalance().subtract(account.getConversionAmount()));
        accountDao.save(accountImp);
        return accountImp;
        }else if (to.equals("EUR")){
            accountImp.setAccountBalanceEur(accountImp.getAccountBalanceEur()
                    .add(responseConfig.getConversionMultiply()
                            .multiply(account.getConversionAmount())));

            accountImp.setAccountBalance(accountImp.getAccountBalance().subtract(account.getConversionAmount()));
            accountDao.save(accountImp);
            return accountImp;
        }
        return accountImp;
    }

    @Override
    public Account createAccount(int accountNumber) {
        Account account = new Account(accountNumber,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0));
        accountDao.save(account);
        return null;
    }
}
