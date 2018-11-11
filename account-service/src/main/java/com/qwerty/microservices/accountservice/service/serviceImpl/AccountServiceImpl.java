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
    public boolean DecreaseFromBalance(int accountNumber, String amount) {
        if (accountDao.findByAccountNumber(accountNumber) != null) {
            Account account = accountDao.findByAccountNumber(accountNumber);
            if (account.getAccountBalance().compareTo(account.getAccountBalance().subtract(new BigDecimal(amount))) >= 0) {
                account.setAccountBalance(account.getAccountBalance().subtract(new BigDecimal(amount)));
                accountDao.save(account);
                return true;
            } else
                System.out.println("Account has no enough balance");
            return false;
        }
        return false;
    }

    @Override
    public boolean AddToBalance(int accountNumber, String amount) {
        if (accountDao.findByAccountNumber(accountNumber) != null) {
            Account account = accountDao.findByAccountNumber(accountNumber);
            account.setAccountBalance(account.getAccountBalance().add(new BigDecimal(amount)));
            return true;
        }
        return false;
    }

/*    @Override
    public Account ShowCurrencyConvertedBalance(int accountNumber, BigDecimal Balance,String from,String to) {
        Account account = accountDao.findByAccountNumber(accountNumber);

        return account;
    }*/
}
