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
import java.math.BigDecimal;
import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private AccountCurrencyProxy accountCurrencyProxy;

    @MockBean
    private AccountDao accountDao;

    private Account accountDto;
    private Gson gson = new GsonBuilder().create();

    private static final UUID USER_UID = UUID.fromString("21793aac-0171-42c1-9c66-7284ec24a330");
    private static final UUID TEST_UID = UUID.fromString("517df602-4ffb-4e08-9626-2a0cf2db4849");
    private static final int ACCOUNT_NUMBER = 1;
    private static final BigDecimal ACCOUNT_BALANCE = BigDecimal.valueOf(65);
    private static final BigDecimal ACCOUNT_BALANCE_USD = BigDecimal.valueOf(0);
    private static final BigDecimal CONVERSION_MULTIPLY = BigDecimal.valueOf(0.21);
    private static final BigDecimal CONVERSION_AMOUNT = BigDecimal.valueOf(222);

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        accountDto = new Account();
        accountDto.setAccountNumber(1);
        accountDto.setAccountBalance(new BigDecimal(65));
        accountDto.setAccountBalanceUsd(new BigDecimal(0));
        accountDto.setConversionMultiply(new BigDecimal(0.01538));
        accountDto.setAccountBalance(new BigDecimal(0.01538));
    }

    @Test
    public void currencyExchange() throws Exception{

        /*when(accountService.accountCurrencyExchangeUpdate(any(Account.class),"RUB","USD")).thenReturn((Account) response);*/

        String from = "RUB";
        String to = "USD";


        RequestBuilder request = MockMvcRequestBuilders
                .post("/currency-feign/currency-exchange/from/{from}/to/{to}",from,to)
                .sessionAttr("account",this.accountDto)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);


        MvcResult response = mockMvc.perform(request)
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                 .andExpect(jsonPath("$.accountBalance").value(ACCOUNT_BALANCE))
                 .andExpect(jsonPath("$.accountBalanceUsd").value(ACCOUNT_BALANCE_USD))
                 .andExpect(jsonPath("$.conversionMultiply").value(CONVERSION_MULTIPLY))
                 .andReturn();
    }

    @Test
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
}
