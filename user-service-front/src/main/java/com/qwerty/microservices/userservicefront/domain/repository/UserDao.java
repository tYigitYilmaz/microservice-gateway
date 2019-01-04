package com.qwerty.microservices.userservicefront.domain.repository;

import com.qwerty.microservices.userservicefront.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User,Long> {

    User findByUsername(String username);
}
