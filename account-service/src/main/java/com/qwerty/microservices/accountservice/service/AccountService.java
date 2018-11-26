package com.qwerty.microservices.accountservice.service;

import com.qwerty.microservices.accountservice.domain.Account;

import java.math.BigDecimal;
import java.util.List;


public interface AccountService {

    BigDecimal findAccountBalance(int accountNumber);

    Account transactionAccountUpdate(int accountNumber);

    Account accountCurrencyExchangeUpdate(Account account,String from,String to);

    Account createAccount(int accountNumber);

}