package com.qwerty.microservices.transactionpackservice.webTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qwerty.microservices.transactionpackservice.domain.Transaction;
import com.qwerty.microservices.transactionpackservice.domain.TransactionBetweenAccounts;
import com.qwerty.microservices.transactionpackservice.domain.repository.TransactionDao;
import com.qwerty.microservices.transactionpackservice.service.TransactionService;
import com.qwerty.microservices.transactionpackservice.web.AccountProxy;
import com.qwerty.microservices.transactionpackservice.web.TransactionController;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionDao transactionDao;

    @MockBean
    private AccountProxy accountProxy;

    //https://spring.io/blog/2007/01/15/unit-testing-with-stubs-and-mocks/



    private Gson gson = new GsonBuilder().create();


    private static final int TRANSACTION_NUMBER = 1;
    private static final int ACCOUNT_NUMBER = 1;
    private static final int ACCOUNT_NUMBER_FROM = 1;
    private static final int ACCOUNT_NUMBER_TO = 2;
    private static final BigDecimal TRANSACTION_BALANCE= BigDecimal.valueOf(1);
    private static final BigDecimal TRANSACTION_AMOUNT = BigDecimal.valueOf(1);

    @Test
    public void depositTransaction() throws Exception{
        Transaction resp = transactionService.createTransaction(ACCOUNT_NUMBER,TRANSACTION_AMOUNT);

        when(transactionService.deposit(any(Transaction.class))).thenReturn(resp);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/transaction-feign/deposit")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(new Transaction()));

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                /*.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.transactionNumber").value(TRANSACTION_NUMBER))*/
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.transactionAmount").value(TRANSACTION_AMOUNT))
                .andReturn();
    }

    @Test
    public void withdrawTransaction() throws Exception{
        Transaction resp = transactionService.createTransaction(ACCOUNT_NUMBER,TRANSACTION_AMOUNT);

        when(transactionService.withDraw(any(Transaction.class))).thenReturn(resp);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/transaction-feign/withdraw")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(new Transaction()));

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.transactionNumber").value(TRANSACTION_NUMBER))
                .andExpect(jsonPath("$.accountNumber").value(ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.transactionAmount").value(TRANSACTION_AMOUNT))
                .andReturn();
    }
    @Test
    public void betweenAccountTransaction() throws Exception{
        TransactionBetweenAccounts resp = transactionService.createTransactionBA(ACCOUNT_NUMBER_FROM,ACCOUNT_NUMBER_TO,TRANSACTION_AMOUNT);

        when(transactionService.betweenAccounts(any(TransactionBetweenAccounts.class))).thenReturn(resp);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/transaction-feign/BetweenAccounts")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(new TransactionBetweenAccounts()));

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.transactionNumber").value(TRANSACTION_NUMBER))
                .andExpect(jsonPath("$.accountNumberFrom").value(ACCOUNT_NUMBER_FROM))
                .andExpect(jsonPath("$.accountNumberTo").value(ACCOUNT_NUMBER_TO))
                .andExpect(jsonPath("$.transactionAmount").value(TRANSACTION_AMOUNT))
                .andReturn();
    }
}
