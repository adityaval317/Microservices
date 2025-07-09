package com.aditya.microservices.accounts.service.impl;

import com.aditya.microservices.accounts.dto.*;
import com.aditya.microservices.accounts.entities.Account;
import com.aditya.microservices.accounts.entities.Customer;
import com.aditya.microservices.accounts.exception.CustomerAlreadyExistsException;
import com.aditya.microservices.accounts.exception.ResourceNotFoundException;
import com.aditya.microservices.accounts.mapper.AccountMapper;
import com.aditya.microservices.accounts.mapper.CustomerMapper;
import com.aditya.microservices.accounts.repositories.AccountRepository;
import com.aditya.microservices.accounts.repositories.CustomerRepository;
import com.aditya.microservices.accounts.service.ICustomerService;
import com.aditya.microservices.accounts.service.client.CardsFeignClient;
import com.aditya.microservices.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;
    private final StreamBridge streamBridge;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               AccountRepository accountRepository,
                               CardsFeignClient cardsFeignClient,
                               LoansFeignClient loansFeignClient,
                               StreamBridge streamBridge) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
        this.streamBridge = streamBridge;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerMapper.toCustomer(customerDto, new Customer());
        customerRepository.findByMobileNumber(customer.getMobileNumber()).ifPresent(foundCustomer -> {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists."); });

        Customer savedCustomer = customerRepository.save(customer);

        Account account = addNewAccount(savedCustomer);
        Account savedAccount = accountRepository.save(account);
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Account account, Customer customer) {
        logger.info("Sending communication for new account creation: {}", account.getAccountNumber());
        AccountsMessageDto accountsMessageDto = new AccountsMessageDto(account.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());
        logger.info("AccountsMessageDto: {}", accountsMessageDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMessageDto);
        if (result) {
            logger.info("Communication sent successfully for account: {}", account.getAccountNumber());
        } else {
            logger.error("Failed to send communication for account: {}", account.getAccountNumber());
        }
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

    @Override
    public CustomerDetailsDto fetchCustomerDetailsUsingMobileNumber(String mobileNumber, String correlationId) {
        CustomerDto customerDto = getCustomerUsingMobileNumber(mobileNumber);
        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        customerDetailsDto.setCustomerDto(customerDto);
        ResponseEntity<CardDto> cardResponseEntity = cardsFeignClient.getCardUsingMobileNumber(correlationId, mobileNumber);
        if(null!=cardResponseEntity) {
            CardDto cardDto = cardResponseEntity.getBody();
            customerDetailsDto.setCardDto(cardDto);
        }
        ResponseEntity<LoanDto> loanResponseEntity = loansFeignClient.getLoanUsingMobileNumber(correlationId, mobileNumber);
        if(null!=loanResponseEntity) {
            LoanDto loanDto = loanResponseEntity.getBody();
            customerDetailsDto.setLoanDto(loanDto);
        }
        return customerDetailsDto;
    }

    @Override
    public boolean updateCommunicationSent(int accountNumber) {
        boolean isUpdated = false;
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account", "accountNumber", String.valueOf(accountNumber))
        );
        if(account!=null) {
            account.setCommunicationSent(true);
            accountRepository.save(account);
            isUpdated = true;
            logger.info("Communication sent status updated for account number: {}", accountNumber);
        } else {
            logger.error("Account not found for account number: {}", accountNumber);
        }
        return isUpdated;
    }


}
