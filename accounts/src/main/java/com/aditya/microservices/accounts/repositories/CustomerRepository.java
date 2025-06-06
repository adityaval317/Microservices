package com.aditya.microservices.accounts.repositories;

import com.aditya.microservices.accounts.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    //derived named method
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
