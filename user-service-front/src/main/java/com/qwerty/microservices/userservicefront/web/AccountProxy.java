package com.qwerty.microservices.userservicefront.web;

import com.qwerty.microservices.userservicefront.domain.User;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient(name = "netflix-zuul-gateway")
@RibbonClient(name = "account-service")
@Component
public interface AccountProxy {

    @RequestMapping(value ="/account-service/register",method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    User accountMatcher(@RequestBody @Valid User request);

}
