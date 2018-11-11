package com.qwerty.mircoservices.userservice.service.serviceImpl;


import com.qwerty.mircoservices.userservice.domain.UserAccount;
import com.qwerty.mircoservices.userservice.domain.repository.UserAccountDao;
import com.qwerty.mircoservices.userservice.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDao userAccountDao;


    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    public UserAccountDao getUserAccountDao() {
        return userAccountDao;
    }

    @Override
    public void createUserAccount(String username, String firstName, String lastName) {

        UserAccount userAccount = new UserAccount(username, firstName, lastName);

        userAccountDao.save(userAccount);
    }
}
