package com.qwerty.microservices.transactionpackservice.web;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionDao;
import com.qwerty.microservices.transactionpackservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class TransactionController {


    private TransactionService transactionService;
    private AccountProxy accountProxy;
    private TransactionDao transactionDao;

    @Autowired
    private void SetTransactionService(TransactionService transactionService){
        this.transactionService=transactionService;
    }
    private TransactionService getTransactionService(){
        return transactionService;
    }

    @Autowired
    private void SetAccountProxy(AccountProxy accountProxy){
        this.accountProxy = accountProxy;
    }


    private AccountProxy getAccountProxy(){
        return accountProxy;
    }

    @Autowired
    private void SetTransactionDao(TransactionDao transactionDao){
        this.transactionDao=transactionDao;
    }


    private TransactionDao getTransactionDao(){
        return transactionDao;
    }

   /* @PostMapping(value = "/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Transaction depositTransactionCreate
            (@PathVariable(value = "transactionNumber") String transationNumber
                    , @PathVariable(value = "transactionType") String transactionType
                    , @PathVariable(value = "accountNumber") String accountNumber
                    , @PathVariable(value = "transactionAmount") String transactionAmount
            ){


        return new Transaction(Integer.valueOf(accountNumber),transactionType,Integer.valueOf(transactionAmount)
                ,new BigDecimal(transactionAmount));
    }*/

    @PostMapping(value = "/transaction-feign/transaction/{transactionType}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Transaction depositTransaction( @PathVariable(value = "transactionType") String transactionType
            , @PathVariable(value = "accountNumber") int accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount)
    {
        Transaction response = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber);

        if (response==null){
            Transaction responseImpl = transactionService.createTransaction(accountNumber,new BigDecimal(0)
                    ,transactionType,new BigDecimal(transactionAmount));

            Transaction updatedResponse = accountProxy.accountMatcher(responseImpl);

            return   transactionService.deposit(responseImpl.getTransactionNumber(),accountNumber,
                    updatedResponse.getAccountBalance(),transactionType,new BigDecimal(transactionAmount));
        }

        Transaction updatedResponse = accountProxy.accountMatcher(response);

        return transactionService.deposit(response.getTransactionNumber(),accountNumber,
                updatedResponse.getAccountBalance(),transactionType,new BigDecimal(transactionAmount));
    }

    @PostMapping(value ="/transaction/deposit")
     public  @ResponseBody Transaction transactionConfirm(@RequestBody @Valid Transaction request){

        return transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(request.getAccountNumber());
    }

    @GetMapping(value = "/transaction")
    public String transaction() {
        return "transaction";
    }
}