package com.qwerty.microservices.accountservice.web;


import com.qwerty.microservices.accountservice.domain.Account;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.validation.Valid;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "netflix-zuul-gateway")
@Component
public interface TransactionProxy {

    @RequestMapping(value ="/transaction-pack-service/transaction",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Account transactionConfirm(@RequestBody @Valid Account request);
}


