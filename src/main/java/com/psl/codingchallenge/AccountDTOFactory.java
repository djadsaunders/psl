package com.psl.codingchallenge;

import java.util.ArrayList;
import java.util.List;

import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.dto.AccountDTO;

/**
 * Static methods for creating Account DTOs
 */
public abstract class AccountDTOFactory {

    /**
     * Create a DTO from an Account object
     * @param account Account object
     * @return DTO
     */
    public static AccountDTO createDTO(Account account) {
        return new AccountDTO(account.getId(), account.getAccountNumber());
    }

    /**
     * Create a List of DTOs from a List of Account objects
     * @param accounts List of Accounts
     * @return List of DTOs
     */
    public static List<AccountDTO> createDTOList(List<Account> accounts) {
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOs.add(new AccountDTO(account.getId(), account.getAccountNumber()));
        }
        return accountDTOs;
    }
}