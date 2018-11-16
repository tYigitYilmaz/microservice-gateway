package com.qwerty.microservices.accountservice.web;


import com.qwerty.microservices.accountservice.domain.Account;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "netflix-zuul-gateway")
@Component
public interface TransactionProxy {

    @PostMapping(value ="/transaction-pack-service/transaction/deposit")
    Account transactionConfirm(@RequestBody @Valid Account response);
}


