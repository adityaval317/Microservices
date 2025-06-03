package com.aditya.microservices.loans.service;

import com.aditya.microservices.loans.dto.CustomerDto;

public interface ICustomerService {
    public void createCustomer(CustomerDto customerDto);

    public CustomerDto getCustomerUsingMobileNumber(String mobileNumber);

    public boolean updateCustomer(CustomerDto customerDto);

    public boolean deleteCustomerUsingMobileNumber(String mobileNumber);
}
