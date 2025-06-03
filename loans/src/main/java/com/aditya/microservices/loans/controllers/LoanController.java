package com.aditya.microservices.loans.controllers;

import com.aditya.microservices.loans.dto.ErrorResponseDto;
import com.aditya.microservices.loans.dto.LoanDto;
import com.aditya.microservices.loans.dto.ResponseDto;
import com.aditya.microservices.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Loan CRUD APIs", description = "Loan CRUD APIs to create, update, delete and fetch loan details")
@RestController
@RequestMapping(path="/loan", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoanController {

    private ILoanService iLoanService;

    @PostMapping("")
    @Operation(summary = "Create a new Loan", description = "Create a new loan")
    @ApiResponse(responseCode = "201", description = "Loan created successfully")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                  @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
                                                       String mobileNumber) {
        iLoanService.createLoan(mobileNumber);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(), HttpStatus.CREATED.name()), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    @Operation(summary = "Get customer and Account Details", description = "Get loan details using mobile number")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<LoanDto> getLoanUsingMobileNumber(
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
            String mobileNumber) {

        return new ResponseEntity<>(iLoanService.getLoanUsingMobileNumber(mobileNumber), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update Loan details", description = "Update loan details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoanDto loanDto) {
        boolean updated = iLoanService.updateLoan(loanDto);
        if (!updated) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.name()), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Loan details", description = "Delete Loan details using loan number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> deleteLoan(
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Loan Number should be 10 digits")
            long loanNumber) {
        boolean deleted = iLoanService.deleteLoanUsingLoanNumber(loanNumber);
        if (!deleted) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.name()), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name()), HttpStatus.OK);
    }
}
