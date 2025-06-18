package com.aditya.microservices.accounts.service.client;

import com.aditya.microservices.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFeignFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardDto> getCardUsingMobileNumber(String correlationId, String mobileNumber) {
        return null;
    }
}
