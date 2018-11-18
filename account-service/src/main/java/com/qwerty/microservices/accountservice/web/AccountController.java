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
    private TransactionProxy transactionProxy;
    private AccountCurrencyProxy accountCurrencyProxy;

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

    @PostMapping(path = "/transaction")
    public @ResponseBody Account retrieveAccountBalance(@RequestBody @Valid Account request){

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @PostMapping(value ="/account-service/currency-exchange")
    public @ResponseBody Account retrieveAccountCurrency(@RequestBody @Valid Account request){

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @PostMapping(value = "/transaction-feign/accountNumber/{accountNumber}")
    public Account accountBalanceUpdate(
            @PathVariable(value = "accountNumber") int accountNumber)
    {
        Account response = accountDao.findByAccountNumber(accountNumber);

        Account responseConfig = transactionProxy.transactionConfirm(response);

        accountService.transactionAccountUpdate(responseConfig.getAccountNumber(),
        responseConfig.getAccountBalance());

        return responseConfig;
    }

    @PostMapping(value = "/currency-feign/currencyExchabge/accountNumber/{accountNumber}/from/{from}/to/{to}/conversionAmount/{conversionAmount}")
    public Account depositTransaction(
            @PathVariable(value = "accountNumber") int accountNumber
            , @PathVariable(value = "from") String from
            , @PathVariable(value = "to") String to
            , @PathVariable(value = "conversionAmount") String conversionAmount)
    {
        Account responseConfig = accountCurrencyProxy.CurrencyConfirm(from,to);

        accountService.accounCurrencyExchange(accountNumber,responseConfig.getConversionMultiply(),new BigDecimal(conversionAmount));

        return responseConfig;
    }
}
