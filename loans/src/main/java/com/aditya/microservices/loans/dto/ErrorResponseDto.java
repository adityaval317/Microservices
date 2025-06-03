package com.aditya.microservices.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(name="Error Response", description = "Error Response")
@Data @AllArgsConstructor
public class ErrorResponseDto {

    @Schema(name = "API Path", example="/api/v1/accounts")
    private String apiPath;

    @Schema(name = "Error Code", example="500")
    private HttpStatus errorCode;

    @Schema(name = "Error Message", example="Internal Server Error")
    private String errorMessage;

    @Schema(name = "Error TimeStamp", example="2023-01-01T00:00:00")
    private LocalDateTime errorTimestamp;
}
