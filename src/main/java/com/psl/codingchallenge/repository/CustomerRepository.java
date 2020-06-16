package com.psl.codingchallenge.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.psl.codingchallenge.domain.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Customers
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findById(long id);
    List<Customer> findByAccountsId(long id);
    Optional<Customer> findByForenameAndSurnameAndDateOfBirth(String forename, String surname, LocalDate dateOfBirth);
}
