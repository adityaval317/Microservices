package com.aditya.microservices.gateway_server.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "X-Correlation-Id";

    public String getCorrelationId(HttpHeaders headers) {
        if(headers.containsKey(CORRELATION_ID) && headers.get(CORRELATION_ID) != null) {
            return headers.getFirst(CORRELATION_ID);
        }
        return null;
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String headerName, String headerValue) {
        return exchange
                .mutate()
                .request(
                    exchange
                        .getRequest()
                        .mutate()
                        .header(headerName, headerValue).build()).build();
    }
}
