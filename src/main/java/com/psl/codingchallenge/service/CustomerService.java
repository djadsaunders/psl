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

/**
 * Services for customers
 */
@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Find customer by ID
     * @param id Customer ID
     * @return Optional object containing customer if found
     */
    @Transactional
    public Optional<Customer> getCustomer(long id) {
        return customerRepository.findById(id);
    }

    /**
     * Find customer(s) for a given account ID
     * @param id Account ID
     * @return List of customers (list will be empty if none found)
     */
    @Transactional
    public List<Customer> findByAccountId(long id) {
        return customerRepository.findByAccountsId(id);
    }

    /**
     * Find all customers
     * @return List of customers (list will be empty if none found)
     */
    @Transactional
    public List<Customer> listAllCustomers() {
        return (List<Customer>)customerRepository.findAll();
    }

    /**
     * Add a new account to a customers account list
     * If the customer is not found, raise an error
     * @param customerId The ID of the customer to which the account should be added
     * @param accountNumber The account number for the new account
     */
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

    /**
     * Given an input (forename, surname and date of birth) check if this is a valid customer
     * Note: validation does not check the associated accounts(s), only the fields listed
     * @param forename Customer's forename
     * @param surname Customer's surname
     * @param dateOfBirth Customer's date of birth
     * @return true or false depending whether this is a valid customer
     */
    @Transactional
    public boolean validate(String forename, String surname, LocalDate dateOfBirth) {
        Optional<Customer> customer = customerRepository.findByForenameAndSurnameAndDateOfBirth(
            forename, surname, dateOfBirth);
        return customer.isPresent();
    }
}