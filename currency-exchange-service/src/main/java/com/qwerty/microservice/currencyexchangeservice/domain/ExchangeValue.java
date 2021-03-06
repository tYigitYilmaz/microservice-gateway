package com.qwerty.microservice.currencyexchangeservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;


@Entity
public class ExchangeValue {

    @Id
    private Long id;

    @Column(name="currency_from")
    private String from;
    @Column(name="currency_to")
    private String to;

    private BigDecimal conversionMultiply;
    private int port;

    public ExchangeValue(){

    }

    public ExchangeValue( String from, String to, BigDecimal conversionMultiply) {
        this.from = from;
        this.to = to;
        this.conversionMultiply = conversionMultiply;
  }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getConversionMultiply() {
        return conversionMultiply;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiply = conversionMultiply;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
