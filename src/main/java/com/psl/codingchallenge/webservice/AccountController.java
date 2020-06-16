package com.psl.codingchallenge.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.psl.codingchallenge.AccountDTOFactory;
import com.psl.codingchallenge.ApplicationException;
import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.dto.AccountDTO;
import com.psl.codingchallenge.service.AccountService;

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
 * Web Controller for operations on Accounts
 */
@RestController
@RequestMapping(value="/account")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * List all accounts
     * If customerId is passed as a query parameter, the list is filtered based on that
     * @param customerId Optional query parameter to filter the accounts
     * @return List of Accounts (as DTOs) or empty list if no accounts found
     */
    @GetMapping
    public List<AccountDTO> listCustomers(@RequestParam(name="customerId", required=false) Long customerId) {
        List<Account> accounts = new ArrayList<>();

        if (customerId == null) {
            accounts = accountService.listAllAccounts();
        }
        else {
            accounts = accountService.findByCustomerId(customerId);
        }

        return AccountDTOFactory.createDTOList(accounts);
    }

    /**
     * Find account by its ID
     * If the account is not found for that ID, an error is raised
     * @param id Account ID passed in as a part of the URL path
     * @return An Account (as a DTO)
     */
    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable long id) {
        logger.debug("Get account: id=" + id);

        Optional<Account> account = accountService.getAccount(id);

        if (account.isPresent()) {
            return AccountDTOFactory.createDTO(account.get());
        }
        else {
            throw new ApplicationException("Could not find account with id " + id);
        }
    }

    /**
     * Add a new account
     * @param accountDTO Object containing account information
     */
    @PostMapping
    public void addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.createAccount(accountDTO.getAccountNumber());
    }

    /**
     * Check if account exists that matches passed in Account
     * @param accountDTO Object containing account information
     * @return A message indicating whether the account exists or not
     */
    @PostMapping("/validate")
    public String validateAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.validate(accountDTO.getAccountNumber()) ? 
            "Account is valid" : "Account is not valid";
    }
}
