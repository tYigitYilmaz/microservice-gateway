package com.qwerty.mircoservices.userservice.domain.repository;



import com.qwerty.mircoservices.userservice.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    int deleteAccountById(Long id);
    Integer countByUsername(String username);
}
