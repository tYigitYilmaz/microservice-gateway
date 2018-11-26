package com.qwerty.microservice.currencyexchangeservice.web;

import com.qwerty.microservice.currencyexchangeservice.domain.ExchangeValue;
import com.qwerty.microservice.currencyexchangeservice.domain.repository.ExchangeValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExchangeValueController {
    private ExchangeValueDao exchangeValueDao;

    @Autowired
    public void setExchangeValueDao(ExchangeValueDao exchangeValueDao) {
        this.exchangeValueDao = exchangeValueDao;
    }

    public ExchangeValueDao getExchangeValueDao() {
        return exchangeValueDao;
    }

    @RequestMapping(value ="/currency-exchange/from/{from}/to/{to}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ExchangeValue retrieveAccountCurrency(@PathVariable(value = "from") String from
            , @PathVariable(value = "to") String to){
        return exchangeValueDao.findByFromAndTo(from,to);
    }
}