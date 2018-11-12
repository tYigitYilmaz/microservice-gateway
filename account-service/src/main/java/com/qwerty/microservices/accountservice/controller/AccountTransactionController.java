package com.qwerty.microservices.accountservice.controller;


import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.TransactionNumber;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.domain.repository.TransactionNumberDao;
import com.qwerty.microservices.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AccountTransactionController {

    private TransactionNumberDao transactionNumberDao;
    private AccountService accountService;
    private AccountDao accountDao;

    @Autowired
    public void setTransactionNumberDao(TransactionNumberDao transactionNumberDao) {
        this.transactionNumberDao = transactionNumberDao;
    }

    public TransactionNumberDao getTransactionNumberDao() {
        return transactionNumberDao;
    }

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

  /*  @RequestMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}/accountBalance/{accountBalance}/amount")
    public TransactionNumber SaveTransactionNumber(@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "accountNumber") String accountNumber) {

        TransactionNumber response = transactionProxy.saveTransactionNumber(transationNumber, accountNumber);

        transactionNumberService.addTransactionNumber(response.getTransactionNumber(),response.getAccountNumber());

        return response;
    }

    @RequestMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}" +
            "/transactionAmount/{transactionAmount}/updatedBalance/{updatedBalance}")
    public Account updatedAccountBalance(@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "updatedBalance") String updatedBalance){

        Account response = transactionProxy.updatedAccountBalance(transationNumber, accountNumber,updatedBalance);

        accountService.transactionAccountUpdate(response.getAccountNumber(),response.getAccountBalance());

        return response;
    }*/

    @PostMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}")
    public  TransactionNumber retrieveAccountBalance(@PathVariable String transactionNumber,@PathVariable String accountNumber){
        Account account = accountDao.findByAccountNumber(Integer.valueOf(accountNumber));

        TransactionNumber transactionNum = new  TransactionNumber(Integer.valueOf(transactionNumber),Integer.valueOf(accountNumber),
                account.getAccountBalance());
        transactionNumberDao.save(transactionNum);

        return transactionNum;
    }
}
