package com.qwerty.mircoservices.userservice.domain.repository;



import com.qwerty.mircoservices.userservice.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
