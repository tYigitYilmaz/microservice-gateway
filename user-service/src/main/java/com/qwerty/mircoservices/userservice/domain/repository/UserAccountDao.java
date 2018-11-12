package com.qwerty.mircoservices.userservice.domain.repository;


import com.qwerty.mircoservices.userservice.domain.UserAccountNumber;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountDao extends CrudRepository<UserAccountNumber, Long> {
    UserAccountNumber findByUsername(String username);

    UserAccountNumber findByAccountNumber(Long accountNumber);
}
