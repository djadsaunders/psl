package com.psl.codingchallenge.dto;

public class AccountDTO {
    private long id;
    private int accountNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountDTO(long id, int accountNumber) {
        this.id = id;
        this.accountNumber = accountNumber;
    }
}