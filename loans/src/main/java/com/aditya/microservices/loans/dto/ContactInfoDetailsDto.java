package com.aditya.microservices.loans.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties ("loans")
public record ContactInfoDetailsDto(String message, ContactDetails contactDetails, OnCallSupport onCallSupport) {
    public record ContactDetails(String name, String email) {}
    public record OnCallSupport(String name, List<String> phone) {}
}
