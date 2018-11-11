package com.qwerty.microservices.accountservice.service.proxy;


import com.qwerty.microservices.accountservice.domain.Account;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "user-service")
public interface UserProxy {

    @GetMapping(value = "/signup/{accountNumber}")
    Account CreateAccount(@PathVariable(value = "accountNumber")Long accountNumber);
}
