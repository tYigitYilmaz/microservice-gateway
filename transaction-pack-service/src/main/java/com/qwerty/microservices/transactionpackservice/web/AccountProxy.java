package com.qwerty.microservices.transactionpackservice.web;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "account-service")
@Component
public interface AccountProxy {

    @PostMapping(value ="/account-service/transaction/deposit")
    Transaction accountMatcher(@RequestBody @Valid Transaction request);

}
