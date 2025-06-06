package com.aditya.microservices.accounts.repositories;

import com.aditya.microservices.accounts.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByCustomerId(Integer customerId);
    Optional<Account> findByAccountNumber(Integer accountNumber);

    @Transactional
    @Modifying
    void deleteByCustomerId(Integer customerId);
}
