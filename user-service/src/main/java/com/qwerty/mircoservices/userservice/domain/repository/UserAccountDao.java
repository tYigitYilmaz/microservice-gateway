package com.qwerty.mircoservices.userservice.domain.repository;


import com.qwerty.mircoservices.userservice.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountDao extends CrudRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);

    UserAccount findByAccountNumber(Long accountNumber);
}
