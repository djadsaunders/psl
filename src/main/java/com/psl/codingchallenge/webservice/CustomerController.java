package com.psl.codingchallenge.webservice;

import java.util.List;
import java.util.Optional;

import com.psl.codingchallenge.ApplicationException;
import com.psl.codingchallenge.CustomerDTOFactory;
import com.psl.codingchallenge.domain.Customer;
import com.psl.codingchallenge.dto.AccountDTO;
import com.psl.codingchallenge.dto.CustomerDTO;
import com.psl.codingchallenge.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Web Controller for operations on Customers
 */
@RestController
@RequestMapping(value="/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * List all customers
     * If accountId is passed as a query parameter, the list is filtered based on that
     * @param accountId Optional query parameter to filter the customers
     * @return List of Customers (as DTOs) or empty list if no customers found
     */
    @GetMapping
    public List<CustomerDTO> listCustomers(@RequestParam(name="accountId", required=false) Long accountId) {
        List<Customer> customers = null;

        if (accountId == null) {
            customers = customerService.listAllCustomers();
        }
        else {
            customers = customerService.findByAccountId(accountId);
        }

        return CustomerDTOFactory.createDTOs(customers);
    }

    /**
     * Find customer by its ID
     * If the customer is not found for that ID, an error is raised
     * @param id Customer ID passed in as a part of the URL path
     * @return A Customer (as a DTO)
     */
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable long id) {
        logger.debug("Get customer: id=" + id);

        Optional<Customer> customer = customerService.getCustomer(id);

        if (customer.isPresent()) {
            return CustomerDTOFactory.createDTO(customer.get());
        }
        else {
            throw new ApplicationException("Could not find customer with id " + id);
        }
    }

    /**
     * Add a new account to a specified customer's accounts list
     * @param id Customer ID
     * @param accountDTO Object containing account information
     */
    @PostMapping("/{id}/accounts")
    public void addNewAccount(@PathVariable long id, @RequestBody AccountDTO accountDTO) {
        logger.debug("Add account " + accountDTO.getAccountNumber() + " to customer " + id);
        
        customerService.addNewAccount(id, accountDTO.getAccountNumber());
    }

    /**
     * Check if customer exists that matches passed in Customer
     * @param customerDTO Object containing customer information
     * @return A message indicating whether the customer exists or not
     */
    @PostMapping("/validate")
    public String validateCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.validate(customerDTO.getForename(), 
            customerDTO.getSurname(), customerDTO.getDateOfBirth()) ? 
            "Customer is valid" : "Customer is not valid";
    }
}
