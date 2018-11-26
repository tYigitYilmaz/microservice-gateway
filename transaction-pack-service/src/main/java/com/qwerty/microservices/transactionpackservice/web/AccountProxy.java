package com.qwerty.microservices.transactionpackservice.web;

import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "account-service")
@Component
public interface AccountProxy {

    @RequestMapping(value ="/account-service/transaction",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Transaction accountMatcher(@RequestBody @Valid Transaction request);

}
