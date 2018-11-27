package com.qwerty.microservice.currencyexchangeservice.webTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qwerty.microservice.currencyexchangeservice.domain.ExchangeValue;
import com.qwerty.microservice.currencyexchangeservice.domain.repository.ExchangeValueDao;
import com.qwerty.microservice.currencyexchangeservice.web.ExchangeValueController;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeValueController.class)
public class ExchangeValueControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeValueDao exchangeValueDao;

    private static final String FROM = "RUB";
    private static final String TO = "EUR";
    private static final BigDecimal CONVERSION_MULTIPLY = BigDecimal.valueOf(0);

    private Gson gson = new GsonBuilder().create();

    @Test
    public void currencyExchange() throws Exception{

        ExchangeValue resp = new ExchangeValue(FROM,TO,CONVERSION_MULTIPLY);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/currency-exchange/from/{from}/to/{to}",FROM,TO)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(gson.toJson(resp));
    }
}
