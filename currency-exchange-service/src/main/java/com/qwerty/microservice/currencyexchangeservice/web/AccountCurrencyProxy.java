package com.qwerty.microservice.currencyexchangeservice.web;


import com.qwerty.microservice.currencyexchangeservice.domain.ExchangeValue;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "account-service")
@Component
public interface AccountCurrencyProxy {

    @PostMapping(value ="/account-service/currency-exchange")
    ExchangeValue accountMatcher(@RequestBody @Valid ExchangeValue request);
}
