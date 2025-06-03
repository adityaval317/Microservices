package com.aditya.microservices.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(name = "Account Object", description = "Account")
@Data
public class AccountDto {

    @Schema(name = "Account Number", example="1234567890", required = true)
    @NotEmpty(message = "Account Number should not be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Account Number should be 10 digits")
    private int accountNumber;

    @Schema(name = "Account Type", example="Checking/Savings", required = true)
    @NotEmpty(message = "Account Type should not be empty")
    private String accountType;

    @Schema(name = "Branch Address", example="123 Main Street, NY, USA", required = true)
    @NotEmpty(message = "Branch Address should not be empty")
    private String branchAddress;
}
