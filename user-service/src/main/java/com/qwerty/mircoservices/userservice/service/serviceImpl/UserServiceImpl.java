/*
package com.qwerty.mircoservices.userservice.service.serviceImpl;

import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.domain.repository.UserDao;
import com.qwerty.mircoservices.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
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
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean checkUserExists(String username, String email) {
        return false;
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return false;
    }

    @Override
    public boolean checkEmailExists(String email) {
        return false;
    }

    @Override
    public void createUser(User user) {
        Optional<User> localUser = userDao.findByUsername(user.getUsername());
        if (localUser != null){
            LOG.info("User with username{} already exist.Nothing will be done.", user.getUsername());
        }else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            for (UserRole ur : userRoles){
                roleDao.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            user.setPrimaryAccount(accountService.createPrimaryAccount());
            user.setSavingsAccount(accountService.createSavingsAccount());

            localUser = userDao.save(user);
        }
        return localUser;
    }
    }

    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public boolean removeAuthenticatedAccount() {
        return false;
    }
}
*/
