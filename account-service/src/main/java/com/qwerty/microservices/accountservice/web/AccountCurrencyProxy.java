package com.qwerty.microservices.accountservice.web;


import com.qwerty.microservices.accountservice.domain.Account;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "currency-exchange-service")
@Component
public interface AccountCurrencyProxy {

    @RequestMapping(value ="/currency-exchange-service/currency-exchange/from/{from}/to/{to}",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Account CurrencyConfirm
            (@PathVariable(value = "from") String from
             , @PathVariable(value = "to") String to);

}
