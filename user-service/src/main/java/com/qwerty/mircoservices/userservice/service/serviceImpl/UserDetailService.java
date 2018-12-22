package com.qwerty.mircoservices.userservice.service.serviceImpl;


import com.qwerty.mircoservices.userservice.domain.Role;
import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.domain.repository.UserDao;
import com.qwerty.mircoservices.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.apache.log4j.Logger;

@Service
public class UserDetailService implements UserDetailsService {

    private final Logger logger = Logger.getLogger(UserService.class);

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user=userDao.findByUsername(s);
        if ( user.isPresent() ) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", s));
        }
    }

    public User findAccountByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername( username );
        if ( user.isPresent() ) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.grantAuthority(Role.ROLE_USER);
        return userDao.save( user );
    }

    @Transactional // To successfully remove the date @Transactional annotation must be added
    public boolean removeAuthenticatedUser() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findAccountByUsername(username);
        int del = userDao.deleteAccountById(user.getId());
        return del > 0;
    }
}
