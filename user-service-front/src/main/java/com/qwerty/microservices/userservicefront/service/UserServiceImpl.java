package com.qwerty.microservices.userservicefront.service;


import com.qwerty.microservices.userservicefront.domain.User;
import com.qwerty.microservices.userservicefront.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl (UserDao userDao,PasswordEncoder passwordEncoder){
        this.userDao=userDao;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public boolean registerUser(User user) {
        User checkedUser = userDao.findByUsername(user.getUsername());
        if (checkedUser==null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAccountNumber((int) Math.random());
            userDao.save(user);
            return true;
        }return false;
    }
}
