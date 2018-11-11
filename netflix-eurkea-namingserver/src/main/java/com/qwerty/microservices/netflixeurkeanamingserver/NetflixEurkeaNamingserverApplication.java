package com.qwerty.microservices.netflixeurkeanamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NetflixEurkeaNamingserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetflixEurkeaNamingserverApplication.class, args);
    }
}
