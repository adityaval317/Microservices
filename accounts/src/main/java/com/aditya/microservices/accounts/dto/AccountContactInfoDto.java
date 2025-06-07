package com.aditya.microservices.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "accounts")
@Getter @Setter
public class AccountContactInfoDto{
    private String message;
    private ContactDetails contactDetails;
    private OnCallSupport onCallSupport;

}

@Getter @Setter
class ContactDetails{
    private String name;
    private String email;
}
@Getter @Setter
class OnCallSupport{
    private String name;
    private List<String> phone;
}
