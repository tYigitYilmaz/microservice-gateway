package com.qwerty.microservice.transactionservice.service.proxy;


import com.qwerty.microservice.transactionservice.domain.Transaction;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "netflix-eureka-namingserver")
@RibbonClient(name = "account-service")
@Component
public interface AccountServiceProxy {

    @GetMapping(value = "/transaction-service/transaction/{transactionType}/transactionNumber/{transactionNumber},/accountNumber/{accountNumber}" +
            "/transactionAmount/{transactionAmount}/updatedBalance/{updatedBalance}")
     Transaction deposit(@PathVariable(value = "transactionNumber") String transationNumber
            , @PathVariable(value = "accountNumber") String accountNumber
            , @PathVariable(value = "transactionAmount") String transactionAmount
            , @PathVariable(value = "updatedBalance") String updatedBalance);
}
