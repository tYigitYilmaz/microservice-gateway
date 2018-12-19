package com.qwerty.mircoservices.userservice.controller;


import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.service.UserAccountService;
import com.qwerty.mircoservices.userservice.service.UserService;
import com.qwerty.mircoservices.userservice.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PreAuthorize("hasRole('REGISTER')")
    @PostMapping("/api/account/register")
    public ResponseEntity<User> registerAccount(@RequestBody User user  ) {
        user = userServiceImpl.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /*@PreAuthorize("isFullyAuthenticated()")
    @DeleteMapping("/api/account/remove")
    public ResponseEntity<HomeController.RestMsg> removeUser(Principal principal){
        boolean isDeleted = UserServiceImpl.removeAuthenticatedUser();
        if ( isDeleted ) {
            return new ResponseEntity<>(
                    new HomeController.RestMsg(String.format("[%s] removed.", principal.getName())),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<HomeController.RestMsg>(
                    new HomeController.RestMsg(String.format("An error ocurred while delete [%s]", principal.getName())),
                    HttpStatus.BAD_REQUEST
            );
        }
    }*/
}
