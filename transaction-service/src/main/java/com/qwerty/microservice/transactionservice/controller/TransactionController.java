package com.qwerty.microservice.transactionservice.controller;



import com.qwerty.microservice.transactionservice.domain.Transaction;
import com.qwerty.microservice.transactionservice.domain.TransactionBalance;
import com.qwerty.microservice.transactionservice.service.TransactionService;
import com.qwerty.microservice.transactionservice.service.proxy.AccountServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransactionController {


    private TransactionService transactionService;

    private AccountServiceProxy accountServiceProxy;

    @Autowired
    public void TransactionService(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    public TransactionService getTransactionService(){
        return transactionService;
    }

   @Autowired
    public void AccountServiceProxy(AccountServiceProxy accountServiceProxy){
        this.accountServiceProxy=accountServiceProxy;
    }

    public AccountServiceProxy getAccountServiceProxy(){
        return accountServiceProxy;
    }


   /* @PostMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}/updatedBalance/{updatedBalance}")
    public Transaction depositTransaction
            (@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "transactionType") String transactionType
            , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount
            , @PathVariable(value = "updatedBalance") String updatedBalance){
        Transaction response = new Transaction(Integer.valueOf(transationNumber),transactionType,
                Integer.valueOf(accountNumber),
                new BigDecimal(transactionAmount),
                new BigDecimal(updatedBalance));
        transactionService.createTransaction(response.getTransactionNumber(),transactionType,response.getAccountNumber()
                ,response.getAmount(),response.getAvailableBalance());
        return response ;
    }*/


    @PostMapping(value = "/transaction-feign/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Transaction depositTransaction(@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "transactionType") String transactionType
            , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount)
          {
        TransactionBalance response =  accountServiceProxy.accountMatcher(transationNumber,accountNumber);
        transactionService.deposit(Integer.valueOf(accountNumber),transactionType,Integer.valueOf(transactionAmount)
                ,new BigDecimal(transactionAmount),response.getAccountBalance() );

        return  new Transaction(Integer.valueOf(accountNumber),transactionType,Integer.valueOf(transactionAmount)
                ,new BigDecimal(transactionAmount));
    }


    @GetMapping(value = "/transaction")
    public String transaction() {
        return "transaction";
    }
}

















