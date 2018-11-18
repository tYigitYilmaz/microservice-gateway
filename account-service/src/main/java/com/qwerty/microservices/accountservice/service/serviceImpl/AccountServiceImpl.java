package com.qwerty.microservices.accountservice.service.serviceImpl;


import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;


    @Autowired
    public void SetAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Override
    public BigDecimal findAccountBallance(int accountNumber) {
        Account account = accountDao.findByAccountNumber(accountNumber);
        return account.getAccountBalance();
    }

    @Override
    public Account transactionAccountUpdate(int accountNumber, BigDecimal updatedBalance) {
       Account account = accountDao.findByAccountNumber(accountNumber);
       account.setAccountBalance(updatedBalance);
       accountDao.save(account);
       return account;
    }

    @Override
    public Account accounCurrencyExchange(int accountNumber, BigDecimal conversionMultiply,BigDecimal conversionAmount) {
        Account account = accountDao.findByAccountNumber(accountNumber);
        account.setAccountBalanceUsd(account.getConversionMultiply().multiply(conversionAmount));
        account.setAccountBalance(account.getAccountBalance().subtract(conversionAmount));
        accountDao.save(account);
        return null;
    }
}
