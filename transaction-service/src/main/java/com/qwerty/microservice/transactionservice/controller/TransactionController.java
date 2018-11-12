package com.qwerty.microservice.transactionservice.controller;



import com.qwerty.microservice.transactionservice.domain.Transaction;
import com.qwerty.microservice.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public void TransactionService(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    public TransactionService getTransactionService(){
        return transactionService;
    }


    @PostMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}/updatedBalance/{updatedBalance}")
    public Transaction depositTransaction
            (@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount
            , @PathVariable(value = "updatedBalance") String updatedBalance){
        Transaction response = transactionService.createTransaction(Integer.valueOf(transationNumber),
                Integer.valueOf(accountNumber),
                new BigDecimal(transactionAmount),
                new BigDecimal(updatedBalance));
        return response ;
    }

    @GetMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}" +
            "/transactionAmount/{transactionAmount}/updatedBalance/{updatedBalance}")
    public Transaction deposit(@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount
            , @PathVariable(value = "updatedBalance") String updatedBalance){
        Transaction response = transactionService.createTransaction(Integer.valueOf(transationNumber),
                Integer.valueOf(accountNumber),
                new BigDecimal(transactionAmount),
                new BigDecimal(updatedBalance));
        transactionService.deposit(response.getAccountNumber(),response.getTransactionNumber()
                ,response.getAvailableBalance(),transactionAmount);
        return  response;
    }

    @GetMapping(value = "/transaction")
    public String transaction() {
        return "transaction";
    }
}
