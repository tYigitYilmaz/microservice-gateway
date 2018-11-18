package com.qwerty.microservice.currencyexchangeservice.domain.repository;

import com.qwerty.microservice.currencyexchangeservice.domain.ExchangeValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExchangeValueDao extends CrudRepository<ExchangeValue, Long> {

    ExchangeValue findByFromAndTo(String from, String to);
}