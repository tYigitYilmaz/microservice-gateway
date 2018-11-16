package com.qwerty.microservices.accountservice.web;


import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;


@RestController
public class AccountTransactionController {


    private AccountService accountService;
    private AccountDao accountDao;
    private TransactionProxy transactionProxy;

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



    @PostMapping(path = "/transaction/deposit")
    public @ResponseBody Account retrieveAccountBalance(@RequestBody @Valid Account request){

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @PostMapping(value ="/account-service/currency-exchange")
    public @ResponseBody Account retrieveAccountCurrency(@RequestBody @Valid Account request){

        return accountDao.findByAccountNumber(request.getAccountNumber());
    }

    @PostMapping(value = "/transaction-feign/transaction/{transactionType}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Account depositTransaction(
             @PathVariable(value = "transactionType") String transactionType
            , @PathVariable(value = "accountNumber") int accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount)
    {
        Account response = accountDao.findByAccountNumber(accountNumber);

        Account responseConfig = transactionProxy.transactionConfirm(response);

        accountService.transactionAccountUpdate(responseConfig.getAccountNumber(),
        responseConfig.getAccountBalance());

        return responseConfig;
    }
}
