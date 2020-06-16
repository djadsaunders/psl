package com.psl.codingchallenge.repository;

import java.util.List;
import java.util.Optional;

import com.psl.codingchallenge.domain.Account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Accounts
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByCustomersId(long id);
    Optional<Account> findByAccountNumber(int accountNumber);
}
