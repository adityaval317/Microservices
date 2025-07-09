package com.aditya.microservices.accounts.dto;

public record AccountsMessageDto(int accountNumber, String name, String email, String mobileNumber) {

}