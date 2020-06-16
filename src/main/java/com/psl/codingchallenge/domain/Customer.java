package com.psl.codingchallenge.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name="customer")
public class Customer {

    static Logger logger = LoggerFactory.getLogger(Customer.class);

    @Id
    @Column(name="customer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="forename")
    private String forename;

    @Column(name="surname")
    private String surname;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name="customer_account", 
        joinColumns=@JoinColumn(name="customer_id"),
        inverseJoinColumns=@JoinColumn(name="account_id")
    )
    private Set<Account> accounts;

    public Customer(String forename, String surname, LocalDate dateOfBirth) {
        this.forename = forename;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.accounts = new HashSet<Account>();
    }

    public Customer() {
        // Default constructor required for JPA
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}