package com.aditya.microservices.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name="Customer Details Object", description = "Customer Details with Account, Card and Loan information")
public class CustomerDetailsDto {

    @Schema(name ="Customer Details Object", description = "Customer Details")
    private CustomerDto customerDto;

    @Schema(name="Card Details Object", description="Card Details")
    private CardDto cardDto;

    @Schema(name="Loan Details Object", description="Loan Details")
    private LoanDto loanDto;
}
