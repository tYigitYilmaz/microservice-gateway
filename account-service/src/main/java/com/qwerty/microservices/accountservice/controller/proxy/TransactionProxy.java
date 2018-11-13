package com.qwerty.microservices.accountservice.controller.proxy;



import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.TransactionNumber;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "netflix-eureka-naming-server")
@RibbonClient (name = "transaction-service")
@Component
public interface TransactionProxy {

    @GetMapping(value = "/transaction-service/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}")
    TransactionNumber saveTransactionNumber
            (@PathVariable(value = "transactionNumber")String transationNumber
                    ,@PathVariable(value = "accountNumber")String accountNumber);

    @GetMapping(value = "/transaction-service/transaction/{transactionType}/transactionNumber/{transactionNumber}/accountNumber/{accountNumber}" +
            "/transactionAmount/{transactionAmount}")
    Account updatedAccountBalance
            (@PathVariable(value = "transactionNumber")String transationNumber
             ,@PathVariable(value = "accountNumber")String accountNumber
             ,@PathVariable(value = "updatedBalance")String updatedBalance);
}
