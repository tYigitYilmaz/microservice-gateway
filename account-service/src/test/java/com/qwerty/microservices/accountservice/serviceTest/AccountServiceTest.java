package com.qwerty.microservices.accountservice.serviceTest;

import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @InjectMocks
    AccountService accountService;


    private Account accountDto;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountDto = new Account();
        accountDto.setAccountNumber(1);
        accountDto.setAccountBalance(new BigDecimal(65));
        accountDto.setAccountBalanceUsd(new BigDecimal(0));
        accountDto.setConversionMultiply(new BigDecimal(0.01538));

    }

        @Test
        void testCurrencyExchange(){

        Account updatedAccount = accountService.accounCurrencyExchange(accountDto.getAccountNumber(),accountDto.getConversionMultiply(),new BigDecimal(65));

            BigDecimal expectedRubBallance= updatedAccount.getAccountBalance();
            BigDecimal actualRubBallance=new BigDecimal(0);

            BigDecimal expectedUsdBallance= updatedAccount.getAccountBalanceUsd();
            BigDecimal actualUsdBallance= accountDto.getConversionMultiply().multiply(accountDto.getAccountBalance());

            assertEquals(expectedRubBallance, actualRubBallance);
            assertEquals(expectedUsdBallance, actualUsdBallance);
    }
}
