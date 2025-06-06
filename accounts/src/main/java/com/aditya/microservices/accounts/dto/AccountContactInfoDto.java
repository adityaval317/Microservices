package com.aditya.microservices.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "accounts")
public record AccountContactInfoDto(String message, ContactDetails contactDetails, OnCallSupport onCallSupport) {
    public record ContactDetails(String name, String email) {}
    public record OnCallSupport(String name, List<String> phone) {}
}
