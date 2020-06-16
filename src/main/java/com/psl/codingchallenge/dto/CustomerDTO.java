package com.psl.codingchallenge.dto;

import java.time.LocalDate;

/**
 * DTO for customers
 */
public class CustomerDTO {
    private long id;
    private String forename;
    private String surname;
    private LocalDate dateOfBirth;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String firstname) {
        this.forename = firstname;
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

    public CustomerDTO(long id, String firstname, String surname, LocalDate dateOfBirth) {
        this.id = id;
        this.forename = firstname;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }
}