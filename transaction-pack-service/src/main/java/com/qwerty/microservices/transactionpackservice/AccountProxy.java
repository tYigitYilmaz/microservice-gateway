package com.qwerty.microservices.transactionpackservice;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "account-service")
@Component
public interface AccountProxy {

    @PostMapping(value ="/account-service/transaction/deposit/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}")
    TransactionBalance accountMatcher(@PathVariable(value = "transactionNumber") int transationNumber, @PathVariable(value = "accountNumber") int accountNumber);
}
