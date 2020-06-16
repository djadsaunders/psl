package com.psl.codingchallenge;

import java.time.LocalDate;

import com.psl.codingchallenge.domain.Account;
import com.psl.codingchallenge.domain.Customer;
import com.psl.codingchallenge.repository.AccountRepository;
import com.psl.codingchallenge.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * Main Application class
 */
@SpringBootApplication
public class PSLApplication {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(PSLApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady(ApplicationReadyEvent event) {

		// Seed in some dummy data
		Customer customer1 = new Customer("Eric", "Evans", LocalDate.of(1966,05,25));
		Customer customer2 = new Customer("Mike", "Cohn", LocalDate.of(1971,8,5));
		customer1.getAccounts().add(new Account(1234));
		customer1.getAccounts().add(new Account(5678));
		customer2.getAccounts().add(new Account(9999));
		customerRepository.save(customer1);
		customerRepository.save(customer2);
	}	
}
