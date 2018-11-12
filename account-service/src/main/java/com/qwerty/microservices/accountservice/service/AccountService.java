package com.qwerty.microservices.accountservice.service;

import com.qwerty.microservices.accountservice.domain.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    BigDecimal findAccountBallance(int accountNumber);

    Account transactionAccountUpdate(int accountNumber, BigDecimal updatedBalance);



}
