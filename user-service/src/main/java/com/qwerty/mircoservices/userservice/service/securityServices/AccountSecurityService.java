package com.qwerty.mircoservices.userservice.service.securityServices;
import com.qwerty.mircoservices.userservice.domain.Role;
import com.qwerty.mircoservices.userservice.domain.User;
import com.qwerty.mircoservices.userservice.domain.repository.UserDao;
import com.qwerty.mircoservices.userservice.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountSecurityService  implements UserDetailsService {

    private final Logger logger = Logger.getLogger(UserService.class);

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername( s );
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

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.grantAuthority(Role.ROLE_USER);
        userDao.save(user);
    }

    @Transactional // To successfully remove the date @Transactional annotation must be added
    public boolean removeAuthenticatedAccount() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findAccountByUsername(username);
        int del = userDao.deleteAccountById(user.getId());
        return del > 0;
    }

}
