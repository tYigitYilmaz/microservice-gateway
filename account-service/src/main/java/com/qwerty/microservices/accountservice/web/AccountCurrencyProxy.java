package com.qwerty.microservices.accountservice.web;


import com.qwerty.microservices.accountservice.domain.Account;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "currency-exchange-service")
@Component
public interface AccountCurrencyProxy {

    @PostMapping(value ="/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    Account CurrencyConfirm
            (@PathVariable(value = "from") String from
             , @PathVariable(value = "to") String to);

}
