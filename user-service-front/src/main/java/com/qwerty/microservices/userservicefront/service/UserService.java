package com.qwerty.microservices.userservicefront.service;


import com.qwerty.microservices.userservicefront.domain.User;


public interface UserService {

    boolean registerUser(User user);
}
