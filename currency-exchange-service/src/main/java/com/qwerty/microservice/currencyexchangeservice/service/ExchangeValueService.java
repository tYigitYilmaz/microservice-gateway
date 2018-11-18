package com.qwerty.microservice.currencyexchangeservice.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface ExchangeValueService {

    void retrieveExchangeAccount(BigDecimal accountBallance, BigDecimal conversionMultiple);
}
