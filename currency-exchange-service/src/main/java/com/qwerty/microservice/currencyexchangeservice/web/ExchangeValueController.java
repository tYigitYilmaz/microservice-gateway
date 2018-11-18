package com.qwerty.microservice.currencyexchangeservice.web;

import com.qwerty.microservice.currencyexchangeservice.domain.ExchangeValue;
import com.qwerty.microservice.currencyexchangeservice.domain.repository.ExchangeValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ExchangeValueController {
    private ExchangeValueDao exchangeValueDao;

    @Autowired
    public void setExchangeValueDao(ExchangeValueDao exchangeValueDao) {
        this.exchangeValueDao = exchangeValueDao;
    }

    public ExchangeValueDao getExchangeValueDao() {
        return exchangeValueDao;
    }


    @PostMapping(value ="/currency-exchange")
    public @ResponseBody ExchangeValue retrieveAccountCurrency(@RequestBody @Valid ExchangeValue request){

        return exchangeValueDao.findByFromAndTo(request.getFrom(),request.getTo());
    }
}