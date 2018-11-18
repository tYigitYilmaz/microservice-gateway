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

    @PostMapping(value = "/transaction-feign/transaction-deposit/{deposit}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Transaction depositTransaction( @PathVariable(value = "deposit") String deposit
            , @PathVariable(value = "accountNumber") int accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount)
    {
        Transaction response = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber);

        if (response==null){
            Transaction responseImpl = transactionService.createTransaction(accountNumber,new BigDecimal(0)
                    ,deposit,new BigDecimal(transactionAmount));

            Transaction updatedResponse = accountProxy.accountMatcher(responseImpl);

            return   transactionService.deposit(responseImpl.getTransactionNumber(),accountNumber,
                    updatedResponse.getAccountBalance(),deposit,new BigDecimal(transactionAmount));
        }

        Transaction updatedResponse = accountProxy.accountMatcher(response);

        return transactionService.deposit(response.getTransactionNumber(),accountNumber,
                updatedResponse.getAccountBalance(),deposit,new BigDecimal(transactionAmount));
    }

    @PostMapping(value = "/transaction-feign/transaction-withdraw/{withdraw}/accountNumber/{accountNumber}/transactionAmount/{transactionAmount}")
    public Transaction withDraw( @PathVariable(value = "withdraw") String withdraw
            , @PathVariable(value = "accountNumber") int accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount)
    {
        Transaction response = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumber);

        if (response==null){
            Transaction responseImpl = transactionService.createTransaction(accountNumber,new BigDecimal(0)
                    ,withdraw,new BigDecimal(transactionAmount));

            Transaction updatedResponse = accountProxy.accountMatcher(responseImpl);

            return   transactionService.withDraw(responseImpl.getTransactionNumber(),accountNumber,
                    updatedResponse.getAccountBalance(),withdraw,new BigDecimal(transactionAmount));
        }

        Transaction updatedResponse = accountProxy.accountMatcher(response);

        return transactionService.withDraw(response.getTransactionNumber(),accountNumber,
                updatedResponse.getAccountBalance(),withdraw,new BigDecimal(transactionAmount));
    }

    @PostMapping(value = "/transaction-feign/transaction-BetweenAccounts/{BetweenAccounts}/accountNumberFrom/{accountNumberFrom}/accountNumberTo/{accountNumberTo}/transactionAmount/{transactionAmount}")
    public Transaction betweenAccounts( @PathVariable(value = "BetweenAccounts") String BetweenAccounts
            , @PathVariable(value = "accountNumberFrom") int accountNumberFrom
            , @PathVariable(value = "accountNumberTo") int accountNumberTo
            , @PathVariable(value = "transactionAmount") String transactionAmount)
    {
        Transaction responseFrom = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumberFrom);
        Transaction responseTo = transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(accountNumberTo);

        if (responseFrom==null|| responseTo==null){
            Transaction responseFromImpl = transactionService.createTransaction(accountNumberFrom,new BigDecimal(0)
                    ,BetweenAccounts,new BigDecimal(transactionAmount));
            Transaction responseToImpl = transactionService.createTransaction(accountNumberTo,new BigDecimal(0)
                    ,BetweenAccounts,new BigDecimal(transactionAmount));

            Transaction updatedResponseFrom = accountProxy.accountMatcher(responseFromImpl);
            Transaction updatedResponseTo = accountProxy.accountMatcher(responseToImpl);

            return transactionService.betweenAccounts(responseFromImpl.getTransactionNumber(),accountNumberFrom
                    ,accountNumberTo,updatedResponseFrom.getAccountBalance(),updatedResponseTo.getAccountBalance()
                    ,BetweenAccounts,new BigDecimal(transactionAmount));
        }

        Transaction updatedResponseFrom = accountProxy.accountMatcher(responseFrom);
        Transaction updatedResponseTo = accountProxy.accountMatcher(responseTo);


        return transactionService.betweenAccounts(responseFrom.getTransactionNumber(),accountNumberFrom
                ,accountNumberTo,updatedResponseFrom.getAccountBalance(),updatedResponseTo.getAccountBalance()
                ,BetweenAccounts,new BigDecimal(transactionAmount));
    }

    @PostMapping(value ="/transaction")
     public  @ResponseBody Transaction transactionConfirm(@RequestBody @Valid Transaction request){

        return transactionDao.findFirstByAccountNumberOrderByTransactionNumberDesc(request.getAccountNumber());
    }

    @GetMapping(value = "/transaction")
    public String transaction() {
        return "transaction";
    }
}