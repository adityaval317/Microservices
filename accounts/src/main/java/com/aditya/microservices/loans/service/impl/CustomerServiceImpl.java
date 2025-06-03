package com.aditya.microservices.loans.service.impl;

import com.aditya.microservices.loans.dto.AccountDto;
import com.aditya.microservices.loans.dto.CustomerDto;
import com.aditya.microservices.loans.entities.Account;
import com.aditya.microservices.loans.entities.Customer;
import com.aditya.microservices.loans.exception.CustomerAlreadyExistsException;
import com.aditya.microservices.loans.exception.ResourceNotFoundException;
import com.aditya.microservices.loans.mapper.AccountMapper;
import com.aditya.microservices.loans.mapper.CustomerMapper;
import com.aditya.microservices.loans.repositories.AccountRepository;
import com.aditya.microservices.loans.repositories.CustomerRepository;
import com.aditya.microservices.loans.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.toCustomer(customerDto, new Customer());
        customerRepository.findByMobileNumber(customer.getMobileNumber()).ifPresent(foundCustomer -> {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists."); });

        Customer savedCustomer = customerRepository.save(customer);

        Account account = addNewAccount(savedCustomer);
        accountRepository.save(account);
    }

    @Override
    public CustomerDto getCustomerUsingMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", String.valueOf(customer.getCustomerId())));
        CustomerDto customerDto = CustomerMapper.toCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.toAccountDto(account, new AccountDto()));
        return customerDto;
    }

    private Account addNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        int accountNumber = 1000000000 + new Random().nextInt(90000000);
        account.setAccountNumber(accountNumber);
        account.setAccountType("Savings");
        account.setBranchAddress("123 Main Street, NY, USA");
        return account;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) {
        boolean updated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            Account existingAccount = accountRepository.findByAccountNumber(accountDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", String.valueOf(accountDto.getAccountNumber())));

            Account updatedAccount = AccountMapper.toAccount(accountDto, existingAccount);
            accountRepository.save(updatedAccount);
            Customer existingCustomer = customerRepository.findById(updatedAccount.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", String.valueOf(updatedAccount.getCustomerId())));
            Customer updatedCustomer = CustomerMapper.toCustomer(customerDto, existingCustomer);
            customerRepository.save(updatedCustomer);
            updated = true;
        }
        return updated;
    }

    @Override
    public boolean deleteCustomerUsingMobileNumber(String mobileNumber) {
        boolean deleted = false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        if(customer!=null){
            Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", String.valueOf(customer.getCustomerId())));
            accountRepository.delete(account);
            customerRepository.delete(customer);
            deleted = true;
        }
        return deleted;
    }

}
