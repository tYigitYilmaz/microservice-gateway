package com.qwerty.mircoservices.userservice.service;


import com.qwerty.mircoservices.userservice.domain.User;

public interface UserService {

    User findByEmail(String email);

    User findByUsername(String username);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    void createUser(User user);


}
