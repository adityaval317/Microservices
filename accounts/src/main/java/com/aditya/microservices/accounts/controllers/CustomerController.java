package com.aditya.microservices.accounts.controllers;

import com.aditya.microservices.accounts.dto.AccountContactInfoDto;
import com.aditya.microservices.accounts.dto.CustomerDto;
import com.aditya.microservices.accounts.dto.ErrorResponseDto;
import com.aditya.microservices.accounts.dto.ResponseDto;
import com.aditya.microservices.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer CRUD APIs", description = "Customer CRUD APIs to create, update, delete and fetch customer and account details")
@RestController
@RequestMapping(path="/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CustomerController {

    private ICustomerService iCustomerService;

    private Environment env;

    private AccountContactInfoDto accountContactInfoDto;

    @Autowired
    public CustomerController(ICustomerService iCustomerService, Environment env, AccountContactInfoDto accountContactInfoDto) {
        this.iCustomerService = iCustomerService;
        this.env = env;
        this.accountContactInfoDto = accountContactInfoDto;
    }

    @Value("${build.version}")
    private String buildVersion;

    @PostMapping("")
    @Operation(summary = "Create a new customer", description = "Create a new customer and account")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        iCustomerService.createCustomer(customerDto);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(), HttpStatus.CREATED.name()), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    @Operation(summary = "Get customer and Account Details", description = "Get customer and account details using mobile number")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<CustomerDto> getCustomerUsingMobileNumber(
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
            String mobileNumber) {
        return new ResponseEntity<>(iCustomerService.getCustomerUsingMobileNumber(mobileNumber), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update customer and account details", description = "Update customer and account details using mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {
        boolean updated = iCustomerService.updateCustomer(customerDto);
        if (!updated) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.name()), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete customer and account details", description = "Delete customer and account details using mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> deleteCustomer(
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
            String mobileNumber) {
        boolean deleted = iCustomerService.deleteCustomerUsingMobileNumber(mobileNumber);
        if (!deleted) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.name()), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name()), HttpStatus.OK);
    }

    @GetMapping("/build/version")
    @Operation(summary = "Get Build Version", description = "Get Build Version")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<String> getBuildVersion() {
        return new ResponseEntity<>(buildVersion, HttpStatus.OK);
    }

    @GetMapping("/java/version")
    @Operation(summary = "Get Java Version", description = "Get Java Version")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<String> getJavaVersion() {
        return new ResponseEntity<>(env.getProperty("JAVA_HOME"), HttpStatus.OK);
    }

    @GetMapping("/support-info")
    @Operation(summary = "Get Support Contact Details", description = "Get Support Contact Details")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<AccountContactInfoDto> getSupportContactDetails() {
        return new ResponseEntity<>(accountContactInfoDto, HttpStatus.OK);

    }
}

