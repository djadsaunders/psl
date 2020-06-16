package com.psl.codingchallenge.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Services for Accounts
 */
@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Find an account by its ID
     * @param id Account ID
     * @return Optional object containing Account if found
     */
    @Transactional
    public Optional<Account> getAccount(long id) {
        return accountRepository.findById(id);
    }

    /**
     * Find all accounts for a given customer
     * @param id Customer ID
     * @return List of Accounts (empty list if no accounts found)
     */
    @Transactional
    public List<Account> findByCustomerId(long id) {
        return accountRepository.findByCustomersId(id);
    }

    /**
     * Find all accounts
     * @return List of Accounts (empty list if no accounts found)
     */
    @Transactional
    public List<Account> listAllAccounts() {
        return (List<Account>)accountRepository.findAll();
    }

    /**
     * Create a new account
     * @param accountNumber
     * @return The new account
     */
    @Transactional
    public Account createAccount(int accountNumber) {
        return accountRepository.save(new Account(accountNumber));
    }

    /**
     * Given an input (account number) check if this is a valid account
     * Note: validation does not check the associated customer(s)
     * @return true or false depending whether this is a valid account
     */
    @Transactional
    public boolean validate(int accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        return account.isPresent();
    }
}