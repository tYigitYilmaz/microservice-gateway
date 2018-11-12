package com.qwerty.microservice.transactionservice.service.proxy;

import com.qwerty.microservice.transactionservice.domain.TransactionBalance;
import com.qwerty.microservice.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "netflix-eureka-naming-server")
@RibbonClient(name = "account-service")
@Service
public class AccountServiceImpl implements AccountServiceProxy {
    private AccountServiceProxy accountServiceProxy;
    private TransactionService transactionService;

    @Autowired
    public void AccountServiceProxy(AccountServiceProxy accountServiceProxy){
        this.accountServiceProxy=accountServiceProxy;
    }

    public AccountServiceProxy getAccountServiceProxy(){
        return accountServiceProxy;
    }

    @Autowired
    public void TransactionService(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    public TransactionService getTransactionService(){
        return transactionService;
    }


    @Override
    public TransactionBalance accountMatcher(String transactionNumber,String accountNumber) {

        TransactionBalance response = accountServiceProxy.accountMatcher(transactionNumber,accountNumber);

        return new TransactionBalance(Integer.valueOf(transactionNumber),response.getAccountBalance());
    }
}
