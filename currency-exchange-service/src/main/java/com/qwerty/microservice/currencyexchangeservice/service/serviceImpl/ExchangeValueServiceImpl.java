package com.qwerty.microservice.currencyexchangeservice.service.serviceImpl;

import com.qwerty.microservice.currencyexchangeservice.service.ExchangeValueService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExchangeValueServiceImpl implements ExchangeValueService {

    @Override
    public void retrieveExchangeAccount(BigDecimal accountBallance, BigDecimal conversionMultiple) {

    }
}


