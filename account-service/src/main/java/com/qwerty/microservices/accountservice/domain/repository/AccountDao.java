package com.qwerty.microservices.accountservice.domain.repository;

import com.qwerty.microservices.accountservice.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<Account,Long> {

    Account findByAccountNumber(int accountNumber);

}
