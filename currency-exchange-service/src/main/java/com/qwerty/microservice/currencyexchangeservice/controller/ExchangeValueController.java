package com.qwerty.microservice.currencyexchangeservice.controller;

import com.qwerty.microservice.currencyexchangeservice.domain.ExchangeValue;
import com.qwerty.microservice.currencyexchangeservice.domain.repository.ExchangeValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeValueController {
    private Environment environment;
    private ExchangeValueDao exchangeValueDao;
    /*  private Logger logger = LoggerFactory.getLogger(this.getClass());*/

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return environment;
    }

    @Autowired
    public void setExchangeValueDao(ExchangeValueDao exchangeValueDao) {
        this.exchangeValueDao = exchangeValueDao;
    }

    public ExchangeValueDao getExchangeValueDao() {
        return exchangeValueDao;
    }

    @RequestMapping(value = "/currency-exchange/from/{from}/to/{to}", method = RequestMethod.GET)
    public ExchangeValue retrieveExhangeValue(@PathVariable String from, @PathVariable String to) {
        ExchangeValue exchangeValue = exchangeValueDao.findByFromAndTo(from,to);
        exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        /* logger.info("{}",exchangeValue);
         */
        return exchangeValue;
    }
}