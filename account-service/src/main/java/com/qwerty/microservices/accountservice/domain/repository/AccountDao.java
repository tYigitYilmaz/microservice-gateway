package com.qwerty.microservices.accountservice.domain.repository;

import com.qwerty.microservices.accountservice.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends CrudRepository<Account,Long> {

    Account findByAccountNumber(int accountNumber);

}
