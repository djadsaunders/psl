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

@RestController
@RequestMapping(value="/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

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

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable long id) {
        logger.debug("List customer: id=" + id);

        Optional<Customer> customer = customerService.getCustomer(id);

        if (customer.isPresent()) {
            return CustomerDTOFactory.createDTO(customer.get());
        }
        else {
            throw new ApplicationException("Could not find customer with id " + id);
        }
    }

    @PostMapping("/{id}/accounts")
    public void addNewAccount(@PathVariable long id, @RequestBody AccountDTO accountDTO) {
        logger.debug("Add account " + accountDTO.getAccountNumber() + " to customer " + id);
        
        customerService.addNewAccount(id, accountDTO.getAccountNumber());
    }

    @PostMapping("/validate")
    public String validateCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.validate(customerDTO.getForename(), 
            customerDTO.getSurname(), customerDTO.getDateOfBirth()) ? 
            "Customer is valid" : "Customer is not valid";
    }
}
