package com.qwerty.mircoservices.userservice.domain.repository;

import com.qwerty.mircoservices.userservice.domain.TokenBlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface TokenBlackListRepo extends CrudRepository<TokenBlackList, Long> {
    Optional<TokenBlackList> findByJti(String jti);
    List<TokenBlackList> queryAllByUserIdAndIsBlackListedTrue(Long userId);
    List<TokenBlackList> deleteAllByUserIdAndExpiresBefore (Long userId, Long date);
    /*save(TokenBlackList tokenBlackList);
    List<TokenBlackList> deleteAllByUserIdAndExpiresBefore(Long userId, Long date);*/
        }