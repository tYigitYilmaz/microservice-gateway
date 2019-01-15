package com.qwerty.microservices.transactionpackservice;

import brave.sampler.Sampler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients("com.qwerty.microservices.transactionpackservice")
@EnableEurekaClient
public class TransactionPackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionPackServiceApplication.class, args);
    }
    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }
}
