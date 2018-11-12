package com.qwerty.microservice.currencyexchangeservice.domain.repository;

import com.qwerty.microservice.currencyexchangeservice.domain.ExchangeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueDao extends JpaRepository<ExchangeValue, Long> {

    ExchangeValue findByFromAndTo(String from, String to);
}