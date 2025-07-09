package com.aditya.microservices.notification_service.functions;

import com.aditya.microservices.notification_service.dto.AccountsMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunction {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Function<AccountsMessageDto, AccountsMessageDto> email() {
        return accountsMessageDto -> {
            logger.info("Sending email notification for account: {}", accountsMessageDto.toString());
            // Here you would implement the logic to send an email notification
            // For now, we just log the message
            return accountsMessageDto;
        };
    }

    @Bean
    public Function<AccountsMessageDto, Integer> sms() {
        return accountsMessageDto -> {
            logger.info("Sending SMS notification for account: {}", accountsMessageDto.toString());
            // Here you would implement the logic to send an SMS notification
            // For now, we just log the message
            return accountsMessageDto.accountNumber();
        };
    }
}
