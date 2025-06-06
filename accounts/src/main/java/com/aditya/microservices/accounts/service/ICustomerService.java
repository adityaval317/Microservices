package com.aditya.microservices.accounts.service;

import com.aditya.microservices.accounts.dto.CustomerDto;

public interface ICustomerService {
    public void createCustomer(CustomerDto customerDto);

    public CustomerDto getCustomerUsingMobileNumber(String mobileNumber);

    public boolean updateCustomer(CustomerDto customerDto);

    public boolean deleteCustomerUsingMobileNumber(String mobileNumber);
}
