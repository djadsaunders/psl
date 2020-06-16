package com.psl.codingchallenge.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.psl.codingchallenge.ApplicationException;
import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.domain.Customer;
import com.psl.codingchallenge.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> getCustomer(long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findByAccountId(long id) {
        return customerRepository.findByAccountsId(id);
    }

    public List<Customer> listAllCustomers() {
        return (List<Customer>)customerRepository.findAll();
    }

    @Transactional
    public void addNewAccount(long customerId, int accountNumber) {
        logger.debug("Add account " + accountNumber + " to customer " + customerId);

        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (!customerOptional.isPresent()) throw new ApplicationException("Customer not found with id " + customerId);

        Customer customer = customerOptional.get();

        Account account = new Account(accountNumber);

        customer.getAccounts().add(account);

        for (Account tempAccount : customer.getAccounts()) {
            logger.debug("Found account: " + tempAccount.getAccountNumber());
        }

        customerRepository.save(customer);
    }

    @Transactional
    public boolean validate(String forename, String surname, LocalDate dateOfBirth) {
        Optional<Customer> customer = customerRepository.findByForenameAndSurnameAndDateOfBirth(
            forename, surname, dateOfBirth);
        return customer.isPresent();
    }
}