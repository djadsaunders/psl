package com.psl.codingchallenge;

import java.util.ArrayList;
import java.util.List;

import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.dto.AccountDTO;

public class AccountDTOFactory {
    public static AccountDTO createDTO(Account account) {
        return new AccountDTO(account.getId(), account.getAccountNumber());
    }

    public static List<AccountDTO> createDTOs(List<Account> accounts) {
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOs.add(new AccountDTO(account.getId(), account.getAccountNumber()));
        }
        return accountDTOs;
    }
}