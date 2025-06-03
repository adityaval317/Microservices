package com.aditya.microservices.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(name = "Customer Object", description = "Customer details Object")
@Data
public class CustomerDto {

    @Schema(name = "Customer Name", example="Name", required = true)
    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Schema(name = "Customer Email", example="aa@aa.com", required = true)
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @Schema(name = "Customer Mobile Number", example="9999999999", required = true)
    @NotEmpty(message = "Mobile Number should not be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
    private String mobileNumber;

    @Schema(name = "Account Object", description = "Account")
    private AccountDto accountDto;
}
