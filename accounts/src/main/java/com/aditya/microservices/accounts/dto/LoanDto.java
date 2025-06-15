package com.aditya.microservices.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(name = "Loan Object", description = "Loan")
@Data
public class LoanDto {

    @Schema(name = "Mobile Number", example="9999999999", required = true)
    @NotEmpty(message = "Mobile Number should not be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
    private String mobileNumber;

    @Schema(name = "Loan Number", example="1234567890", required = true)
    @Digits(integer = 10, fraction = 0, message = "Loan Number should be 10 digits")
    private long loanNumber;

    @Schema(name = "Loan Type", example="Home/Car", required = true)
    @NotEmpty(message = "Loan Type should not be empty")
    private String loanType;

    @Schema(name = "Amount Paid", example="0.00", required = true)
    @PositiveOrZero(message = "Amount Paid should be greater than 0")
    private double amountPaid;

    @Schema(name = "Outstanding Amount", example="0.00", required = true)
    @PositiveOrZero(message = "Amount Paid should be greater than 0")
    private double outstandingAmount;

    @Schema(name = "Total Loan Amount", example="0.00", required = true)
    @Positive(message = "Amount Paid should be greater than 0")
    private double totalLoanAmount;
}
