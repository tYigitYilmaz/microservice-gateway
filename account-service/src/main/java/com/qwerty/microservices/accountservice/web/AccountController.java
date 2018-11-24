package com.qwerty.microservices.accountservice.web;


import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;


@RestController
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


    @PostMapping(path = "/transaction")
    public @ResponseBody
    Account retrieveAccountBalance(@RequestBody @Valid Account request) {

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @PostMapping(value = "/account-service/currency-exchange")
    public @ResponseBody
    Account retrieveAccountCurrency(@RequestBody @Valid Account request) {

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @PostMapping(value = "/updateAccountBalance")
    public Account accountBalanceUpdate(@RequestBody @Valid Account request) {
        return accountService.transactionAccountUpdate(request.getAccountNumber());
    }

    @PostMapping(value = "/currency-feign/currency-exchange/from/{from}/to/{to}")
    public Account currencyExchange(
            @RequestBody @Valid Account request,@PathVariable(value = "from") String from
            ,@PathVariable(value = "to") String to) {
        return accountService.accountCurrencyExchangeUpdate(request,from,to);
    }

    @PostMapping(value = "/account/createAccount")
    public Account createAccount(
            @RequestBody @Valid Account request) {
        return accountService.createAccount(request.getAccountNumber());
    }
}
