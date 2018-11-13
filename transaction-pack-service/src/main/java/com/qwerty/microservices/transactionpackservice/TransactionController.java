package com.qwerty.microservices.transactionpackservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransactionController {


    private TransactionService transactionService;
    private AccountProxy accountProxy;


    @Autowired
    private void SetTransactionService(TransactionService transactionService){
        this.transactionService=transactionService;
    }
    private TransactionService getTransactionService(){
        return transactionService;
    }

    @Autowired
    private void SetAccountProxy(AccountProxy accountProxy){
        this.accountProxy=accountProxy;
    }


    private AccountProxy getAccountProxy(){
        return accountProxy;
    }

    @PostMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Transaction depositTransactionCreate
            (@PathVariable(value = "transactionNumber") String transationNumber
                    , @PathVariable(value = "transactionType") String transactionType
                    , @PathVariable(value = "accountNumber") String accountNumber
                    , @PathVariable(value = "transactionAmount") String transactionAmount
            ){


        return new Transaction(Integer.valueOf(accountNumber),transactionType,Integer.valueOf(transactionAmount)
                ,new BigDecimal(transactionAmount));
    }

    @PostMapping(value = "/transaction-feign/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Transaction depositTransaction(@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "transactionType") String transactionType
            , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount)
    {
        TransactionBalance response =  accountProxy.accountMatcher(transationNumber,accountNumber);
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