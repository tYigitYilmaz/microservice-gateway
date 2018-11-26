package com.qwerty.microservices.accountservice.web;


import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountController {


    private AccountService accountService;
    private AccountDao accountDao;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }


    @RequestMapping(path = "/transaction",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Account retrieveAccountBalance(@RequestBody @Valid Account request) {

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @RequestMapping(value = "/account-service/currency-exchange",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    Account retrieveAccountCurrency(@RequestBody @Valid Account request) {

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @RequestMapping(value = "/updateAccountBalance",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Account accountBalanceUpdate(@RequestBody @Valid Account request) {
        return accountService.transactionAccountUpdate(request.getAccountNumber());
    }

    @RequestMapping(value = "/currency-feign/currency-exchange/from/{from}/to/{to}",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Account currencyExchange(
            @RequestBody @Valid Account request,@PathVariable(value = "from") String from
            ,@PathVariable(value = "to") String to) {
        return accountService.accountCurrencyExchangeUpdate(request,from,to);
    }

    @RequestMapping(value = "/account/createAccount",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Account createAccount(
            @RequestBody @Valid Account request) {
        return accountService.createAccount(request.getAccountNumber());
    }
}
