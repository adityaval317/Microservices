package com.aditya.microservices.accounts.service.client;

import com.aditya.microservices.accounts.dto.LoanDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFeignFallback.class)
public interface LoansFeignClient {

    @GetMapping("/loan/fetch")
    public ResponseEntity<LoanDto> getLoanUsingMobileNumber(@RequestHeader("X-Correlation-Id") String correlationId,
                                                            @RequestParam String mobileNumber);
}
