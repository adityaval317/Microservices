package com.aditya.microservices.accounts.service.client;

import com.aditya.microservices.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFeignFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoanDto> getLoanUsingMobileNumber(String correlationId, String mobileNumber) {
        return null;
    }
}
