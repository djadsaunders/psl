package com.psl.codingchallenge;

import java.util.ArrayList;
import java.util.List;

import com.psl.codingchallenge.domain.Customer;
import com.psl.codingchallenge.dto.CustomerDTO;

public class CustomerDTOFactory {
    public static CustomerDTO createDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getForename(), customer.getSurname(),
            customer.getDateOfBirth());
    }

    public static List<CustomerDTO> createDTOs(List<Customer> customers) {
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOs.add(new CustomerDTO(customer.getId(), customer.getForename(), 
                customer.getSurname(), customer.getDateOfBirth()));
        }
        return customerDTOs;
    }
}