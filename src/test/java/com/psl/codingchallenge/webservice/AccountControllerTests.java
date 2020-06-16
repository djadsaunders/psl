package com.psl.codingchallenge.webservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.psl.codingchallenge.ApplicationException;
import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.dto.AccountDTO;
import com.psl.codingchallenge.service.AccountService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTests {
    
    @InjectMocks
    AccountController accountController;

    @Mock
    AccountService accountService;

    @Test
    public void testListAccountsWithNoCustomerId() {
        accountController.listCustomers(null);
        verify(accountService).listAllAccounts();
    }

    @Test
    public void testListAccountsWithCustomerId() {
        long id = 1234;
        accountController.listCustomers(id);
        verify(accountService).findByCustomerId(id);
    }

    @Test
    public void testGetAccountById() {
        Account account = new Account(1234);
        when(accountService.getAccount(anyLong())).thenReturn(Optional.of(account));
        assertNotNull(accountController.getAccountById(1234));
    }

    @Test(expected=ApplicationException.class)
    public void testGetAccountWithInvalidId() {
        when(accountService.getAccount(anyLong())).thenReturn(Optional.empty());
        accountController.getAccountById(1234);
    }

    @Test
    public void testAddAccount() {
        accountController.addAccount(new AccountDTO(1, 1234));
        verify(accountService).createAccount(1234);
    }

    @Test
    public void testValidateAccountTrue() {
        when(accountService.validate(1234)).thenReturn(true);
        String msg = accountController.validateAccount(new AccountDTO(1, 1234));
        verify(accountService).validate(1234);
        assertEquals(msg, "Account is valid");
    }

    @Test
    public void testValidateAccountFalse() {
        when(accountService.validate(1234)).thenReturn(false);
        String msg = accountController.validateAccount(new AccountDTO(1, 1234));
        verify(accountService).validate(1234);
        assertEquals(msg, "Account is not valid");
    }
}