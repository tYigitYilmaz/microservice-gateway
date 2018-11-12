package com.qwerty.microservice.currencyexchangeservice.service;

import java.math.BigDecimal;

public interface ExchangeValueService {

    void retrieveExchangeAccount(BigDecimal accountBallance, BigDecimal conversionMultiple);
}
