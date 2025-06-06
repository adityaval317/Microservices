package com.aditya.microservices.accounts.mapper;

import com.aditya.microservices.accounts.dto.CustomerDto;
import com.aditya.microservices.accounts.entities.Customer;

public class CustomerMapper {

    public static CustomerDto toCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer toCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
