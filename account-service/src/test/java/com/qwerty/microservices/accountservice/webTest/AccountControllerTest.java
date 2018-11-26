package com.qwerty.microservices.accountservice.webTest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qwerty.microservices.accountservice.domain.Account;
import com.qwerty.microservices.accountservice.domain.repository.AccountDao;
import com.qwerty.microservices.accountservice.service.AccountService;
import com.qwerty.microservices.accountservice.web.AccountController;
import com.qwerty.microservices.accountservice.web.AccountCurrencyProxy;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountDao accountDao;

    private Gson gson = new GsonBuilder().create();


    private static final int ACCOUNT_NUMBER = 1;
    private static final BigDecimal ACCOUNT_BALANCE = BigDecimal.valueOf(65);
    private static final BigDecimal ACCOUNT_BALANCE_USD = BigDecimal.valueOf(0);
    private static final BigDecimal ACCOUNT_BALANCE_EUR = BigDecimal.valueOf(0);


    @Test
    public void currencyExchange() throws Exception{
        Account resp = new Account(ACCOUNT_NUMBER,ACCOUNT_BALANCE
                ,ACCOUNT_BALANCE_USD,ACCOUNT_BALANCE_EUR);

        when(accountService.accountCurrencyExchangeUpdate(any(Account.class),anyString(),anyString())).thenReturn(resp);

        String from = "RUB";
        String to = "USD";


        RequestBuilder request = MockMvcRequestBuilders
                .post("/currency-feign/currency-exchange/from/{from}/to/{to}",from,to)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(new Account()));


        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.accountBalance").value(ACCOUNT_BALANCE))
                .andExpect(jsonPath("$.accountBalanceUsd").value(ACCOUNT_BALANCE_USD))
                .andExpect(jsonPath("$.accountBalanceEur").value(ACCOUNT_BALANCE_EUR))
                .andReturn();
    }
}
    /* @Test
    public void currencyProxy() throws Exception{


        RequestBuilder request = MockMvcRequestBuilders
                .post("/currency-exchange-service/currency-exchange/from/{from}/to/{to")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);

        MvcResult response = mockMvc.perform( request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.conversionMultiply").value(CONVERSION_MULTIPLY))
                .andReturn();

        when(accountCurrencyProxy.CurrencyConfirm("RUB","USD")).thenReturn((Account) response);
    }

   @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountDto = new Account();
        accountDto.setAccountNumber(1);
        accountDto.setAccountBalance(new BigDecimal(65));
        accountDto.setAccountBalanceUsd(new BigDecimal(0));
        accountDto.setAccountBalanceEur(new BigDecimal(0));
        accountDto.setConversionMultiply(new BigDecimal(0.21));
        accountDto.setAccountBalance(new BigDecimal(0.01538));
    }

    @Test
    public void currencyExchange() throws Exception{

        when(accountService.accountCurrencyExchangeUpdate(any(Account.class),anyString(),anyString())).thenReturn(accountDto);

        String from = "RUB";
        String to = "USD";


        RequestBuilder request = MockMvcRequestBuilders
                .post("/currency-feign/currency-exchange/from/{from}/to/{to}",from,to)
                .content(gson.toJson(new Account()))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);



        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.accountBalance").value(ACCOUNT_BALANCE))
                .andExpect(jsonPath("$.accountBalanceUsd").value(ACCOUNT_BALANCE_USD))
                .andExpect(jsonPath("$.accountBalanceEur").value(ACCOUNT_BALANCE_EUR))
                .andReturn();
    }*/

