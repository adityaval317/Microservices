package com.aditya.microservices.accounts.functions;

import com.aditya.microservices.accounts.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunction {

    private static final Logger logger = LoggerFactory.getLogger(AccountsFunction.class);

    @Bean
    public Consumer<Long> updateCommunication(ICustomerService customerService) {
        return accountNumber -> {
            logger.info("Received account number: {}", accountNumber);
            boolean isUpdated = customerService.updateCommunicationSent(Math.toIntExact(accountNumber));
            if (isUpdated) {
                logger.info("Communication sent for account number: {}", accountNumber);
            } else {
                logger.warn("Failed to send communication for account number: {}", accountNumber);
            }
        };
    }
}
