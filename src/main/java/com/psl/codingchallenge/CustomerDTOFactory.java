package com.psl.codingchallenge;

import java.util.ArrayList;
import java.util.List;

import com.psl.codingchallenge.domain.Customer;
import com.psl.codingchallenge.dto.CustomerDTO;

/**
 * Static methods for creating Account DTOs
 */
public abstract class CustomerDTOFactory {

    /**
     * Create a DTO from an Customer object
     * @param customer  Customer object
     * @return DTO
     */
    public static CustomerDTO createDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getForename(), customer.getSurname(),
            customer.getDateOfBirth());
    }

    /**
     * Create a List of DTOs from a List of Customer objects
     * @param customers List of Customers
     * @return List of DTOs
     */    
    public static List<CustomerDTO> createDTOs(List<Customer> customers) {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOs.add(new CustomerDTO(customer.getId(), customer.getForename(), 
                customer.getSurname(), customer.getDateOfBirth()));
        }
        return customerDTOs;
    }
}