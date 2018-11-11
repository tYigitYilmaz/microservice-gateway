package com.qwerty.mircoservices.userservice.service.serviceImpl;


import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.domain.repository.UserDao;
import com.qwerty.mircoservices.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;


    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return userDao.findByUsername(username) != null;

    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userDao.findByUsername(username) != null;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userDao.findByEmail(email) != null;
    }

    @Override
    public void createUser(User user) {

        userDao.save(user);
    }
}
