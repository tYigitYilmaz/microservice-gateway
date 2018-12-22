package com.qwerty.mircoservices.userservice.service;


import com.qwerty.mircoservices.userservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public interface UserService {

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    void createUser(User user);

    User registerUser(User user);

    boolean removeAuthenticatedAccount();


}
