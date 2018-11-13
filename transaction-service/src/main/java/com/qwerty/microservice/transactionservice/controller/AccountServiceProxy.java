package com.qwerty.microservice.transactionservice.controller;


import com.qwerty.microservice.transactionservice.domain.TransactionBalance;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "account-service")
@Component
public interface AccountServiceProxy {




    @GetMapping(value ="/account-service/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}")
    TransactionBalance accountMatcher(@PathVariable(value = "transactionNumber") String transationNumber, @PathVariable(value = "accountNumber") String accountNumber);
     /*       , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount
            , @PathVariable(value = "updatedBalance") String updatedBalance}*/
}
