package com.qwerty.microservices.accountservice.controller.proxy;


import com.qwerty.microservices.accountservice.domain.CurrencyExchange;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "netflix-eureka-naming-server")
@RibbonClient(name = "currency-exchange-service")
@Component
public interface CurrencyExchangeProxy {

    @GetMapping(value = "/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    CurrencyExchange retrieveExchangeValue
            (@PathVariable(value = "from") String from, @PathVariable(value = "to") String to);
}

