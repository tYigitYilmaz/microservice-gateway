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

    @Override
    public BigDecimal findAccountBallance(int accountNumber) {
        Account account = accountDao.findByAccountNumber(accountNumber);
        return account.getAccountBalance();
    }
    @Autowired
    public void setAccountCurrencyProxy(AccountCurrencyProxy accountCurrencyProxy) {
        this.accountCurrencyProxy = accountCurrencyProxy;
    }

    public AccountCurrencyProxy getAccountCurrencyProxy() {
        return accountCurrencyProxy;
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
    @PostMapping(value = "/currency-feign/currencyExchange/from/{from}/to/{to}")
    public Account accounCurrencyExchange(int accountNumber, BigDecimal conversionMultiply,BigDecimal conversionAmount
            ,@PathVariable(value = "from") String from
            , @PathVariable(value = "to") String to) {
        Account account = accountDao.findByAccountNumber(accountNumber);
        Account responseConfig = accountCurrencyProxy.CurrencyConfirm(from,to);
        account.setAccountBalanceUsd(account.getAccountBalanceUsd().add(responseConfig.getConversionMultiply().multiply(conversionAmount)));
        account.setAccountBalance(account.getAccountBalance().subtract(conversionAmount));
        accountDao.save(account);
        return null;
    }
}
