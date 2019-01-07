/*
package com.qwerty.mircoservices.userservice.web;


import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.service.serviceImpl.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private UserDetailService userService;

    @Autowired
    public UserController(UserDetailService userService){
        this.userService=userService;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) throws AccountException {
        userService.register(user);
        return user;
    }
}
*/
