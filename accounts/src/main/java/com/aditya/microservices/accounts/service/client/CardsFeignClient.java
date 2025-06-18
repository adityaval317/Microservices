package com.aditya.microservices.accounts.service.client;

import com.aditya.microservices.accounts.dto.CardDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards", fallback = CardsFeignFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/card/fetch", consumes = "application/json")
    public ResponseEntity<CardDto> getCardUsingMobileNumber(@RequestHeader("X-Correlation-Id") String correlationId,
            @RequestParam String mobileNumber);


}
