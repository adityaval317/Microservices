package com.aditya.microservices.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Schema(name = "Card Object", description = "Card")
@Data
public class CardDto {

    @Schema(name = "Mobile Number", example="9999999999", required = true)
    @NotEmpty(message = "Mobile Number should not be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
    private String mobileNumber;

    @Schema(name = "Card Number", example="1234567890", required = true)
    @NotEmpty(message = "Card Number should not be empty")
    @Pattern(regexp = "^[0-9]{16}$", message = "Card Number should be 16 digits")
    private String cardNumber;

    @Schema(name = "Card Type", example="Visa/MasterCard", required = true)
    @NotEmpty(message = "Card Type should not be empty")
    private String cardType;

    @Schema(name = "Amount Used", example="0.00", required = true)
    @PositiveOrZero(message = "Amount Used should be greater than 0")
    private double amountUsed;

    @Schema(name = "Available Amount", example="0.00", required = true)
    @PositiveOrZero(message = "Available Amount should be greater than 0")
    private double availableAmount;

    @Schema(name = "Total Limit", example="0.00", required = true)
    @Positive(message = "Total Limit should be greater than 0")
    private double totalLimit;
}
