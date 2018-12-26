package com.qwerty.mircoservices.userservice;

import com.qwerty.mircoservices.userservice.domain.Role;
import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.service.serviceImpl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@EnableEurekaClient
@EnableAsync
public class UserServiceApplication {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserDetailService userDetailService) {
        return (evt) -> Arrays.asList(
                "user,admin,user1,user2,user3".split(",")).forEach(
                username -> {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword("password");
                    user.setFirstName(username);
                    user.setLastName("LastName");
                    user.grantAuthority(Role.ROLE_USER);
                    if (username.equals("admin"))
                        user.grantAuthority(Role.ROLE_ADMIN);
                    userDetailService.registerUser(user);
                }
        );
    }
}
