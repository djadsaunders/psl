package com.psl.codingchallenge.webservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import com.psl.codingchallenge.ApplicationException;
import com.psl.codingchallenge.domain.Customer;
import com.psl.codingchallenge.dto.AccountDTO;
import com.psl.codingchallenge.dto.CustomerDTO;
import com.psl.codingchallenge.service.CustomerService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTests {
    
    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    @Test
    public void testListCustomersWithNoAccountId() {
        customerController.listCustomers(null);
        verify(customerService).listAllCustomers();
    }

    @Test
    public void testListCustomersWithAccountId() {
        long id = 1234;
        customerController.listCustomers(id);
        verify(customerService).findByAccountId(id);
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = new Customer("forename","surname",LocalDate.now());
        when(customerService.getCustomer(anyLong())).thenReturn(Optional.of(customer));
        assertNotNull(customerController.getCustomerById(1234));
    }

    @Test(expected=ApplicationException.class)
    public void testGetCustomerWithInvalidId() {
        when(customerService.getCustomer(anyLong())).thenReturn(Optional.empty());
        customerController.getCustomerById(1234);
    }

    @Test
    public void testNewAccount() {
        customerController.addNewAccount(1, new AccountDTO(1, 1234));
        verify(customerService).addNewAccount(1, 1234);
    }

    @Test
    public void testValidateAccountTrue() {
        when(customerService.validate(anyString(), anyString(), any())).thenReturn(true);
        CustomerDTO dto = new CustomerDTO(1, "forename", "surname", LocalDate.now());
        String msg = customerController.validateCustomer(dto);
        verify(customerService).validate(dto.getForename(), dto.getSurname(), dto.getDateOfBirth());
        assertEquals(msg, "Customer is valid");
    }

    @Test
    public void testValidateAccountFalse() {
        when(customerService.validate(anyString(), anyString(), any())).thenReturn(false);
        CustomerDTO dto = new CustomerDTO(1, "forename", "surname", LocalDate.now());
        String msg = customerController.validateCustomer(dto);
        verify(customerService).validate(dto.getForename(), dto.getSurname(), dto.getDateOfBirth());
        assertEquals(msg, "Customer is not valid");
    }
}