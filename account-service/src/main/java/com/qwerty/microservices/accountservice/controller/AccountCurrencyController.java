package com.qwerty.microservices.accountservice.controller;

import com.qwerty.microservices.accountservice.domain.CurrencyExchange;
import com.qwerty.microservices.accountservice.service.AccountService;
import com.qwerty.microservices.accountservice.controller.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountCurrencyController {

    private CurrencyExchangeProxy currencyExchangeProxy;
    private AccountService accountService;


    @Autowired
    public void setCurrencyExchangeProxy(CurrencyExchangeProxy currencyExchangeProxy) {
        this.currencyExchangeProxy = currencyExchangeProxy;
    }

    public CurrencyExchangeProxy getCurrencyExchangeProxy() {
        return currencyExchangeProxy;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountService getAccountService() {
        return accountService;
    }


    @RequestMapping(value = "/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}", method = RequestMethod.GET)
    public CurrencyExchange convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyExchange response = currencyExchangeProxy.retrieveExchangeValue(from, to);

        return new CurrencyExchange(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }
}
