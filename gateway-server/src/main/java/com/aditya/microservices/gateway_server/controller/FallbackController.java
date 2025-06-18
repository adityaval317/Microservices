package com.aditya.microservices.gateway_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {


    @RequestMapping("/fallback/contactSupport")
    public Mono<String> contactSupport() {
        return Mono.just("An error occured. Try after somtime or Contact support at 877-844-4334");
    }

}
