package com.qwerty.microservices.userservicefront.web;

import com.qwerty.microservices.userservicefront.domain.User;
import com.qwerty.microservices.userservicefront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService=userService;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public User registerUser(@RequestBody User user){
        userService.registerUser(user);
        return user;
    }
}
