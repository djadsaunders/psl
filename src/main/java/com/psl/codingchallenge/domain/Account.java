package com.psl.codingchallenge.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="account")
public class Account {

    static Logger logger = LoggerFactory.getLogger(Account.class);

    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="account_number")
    private int accountNumber;

    @ManyToMany(mappedBy="accounts")
    private Set<Customer> customers;

    public Account(int accountNumber) {
        this.accountNumber = accountNumber;
        this.customers = new HashSet<Customer>();
    }

    public Account() {
        // Default constructor required for JPA
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}