package com.psl.codingchallenge.webservice;

import java.util.List;
import java.util.Optional;

import com.psl.codingchallenge.AccountDTOFactory;
import com.psl.codingchallenge.ApplicationException;
import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.dto.AccountDTO;
import com.psl.codingchallenge.repository.AccountRepository;
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

@RestController
@RequestMapping(value="/account")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> listCustomers(@RequestParam(name="customerId", required=false) Long customerId) {
        List<Account> accounts = null;

        if (customerId == null) {
            accounts = accountService.listAllAccounts();
        }
        else {
            accounts = accountService.findByCustomerId(customerId);
        }

        return AccountDTOFactory.createDTOs(accounts);
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable long id) {
        logger.debug("List account: id=" + id);

        Optional<Account> account = accountService.getAccount(id);

        if (account.isPresent()) {
            return AccountDTOFactory.createDTO(account.get());
        }
        else {
            throw new ApplicationException("Could not find account with id " + id);
        }
    }

    @PostMapping
    public void addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.createAccount(accountDTO.getAccountNumber());
    }

    @PostMapping("/validate")
    public String validateAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.validate(accountDTO.getAccountNumber()) ? 
            "Account is valid" : "Account is not valid";
    }
}
