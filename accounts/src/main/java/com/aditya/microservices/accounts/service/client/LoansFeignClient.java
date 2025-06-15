package com.aditya.microservices.accounts.service.client;

import com.aditya.microservices.accounts.dto.LoanDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping("/loan/fetch")
    public ResponseEntity<LoanDto> getLoanUsingMobileNumber(@RequestParam String mobileNumber);
}
