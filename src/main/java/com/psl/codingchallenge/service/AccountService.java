package com.psl.codingchallenge.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Optional<Account> getAccount(long id) {
        return accountRepository.findById(id);
    }

    @Transactional
    public List<Account> findByCustomerId(long id) {
        return accountRepository.findByCustomersId(id);
    }

    @Transactional
    public List<Account> listAllAccounts() {
        return (List<Account>)accountRepository.findAll();
    }

    @Transactional
    public Account createAccount(int accountNumber) {
        return accountRepository.save(new Account(accountNumber));
    }

    @Transactional
    public boolean validate(int accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        return account.isPresent();
    }
}