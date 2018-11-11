package com.qwerty.microservices.accountservice.service;

import com.qwerty.microservices.accountservice.domain.Account;

import java.math.BigDecimal;

public interface AccountService {


    boolean DecreaseFromBalance(int accountNumber, String amount);

    boolean AddToBalance(int accountNumber, String amount);
    /*Account ShowCurrencyConvertedBalance(int accountNumber,BigDecimal balance,String from,String to);*/

}
