package com.aditya.microservices.cards.controllers;

import com.aditya.microservices.cards.dto.ContactInfoDetailsDto;
import com.aditya.microservices.cards.dto.ErrorResponseDto;
import com.aditya.microservices.cards.dto.CardDto;
import com.aditya.microservices.cards.dto.ResponseDto;
import com.aditya.microservices.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Card CRUD APIs", description = "Card CRUD APIs to create, update, delete and fetch card details")
@RestController
@RequestMapping(path="/card", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private ICardService iCardService;

    private Environment env;

    private ContactInfoDetailsDto contactInfoDetailsDto;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    public CardController(ICardService iCardService, Environment env, ContactInfoDetailsDto contactInfoDetailsDto) {
        this.iCardService = iCardService;
        this.env = env;
        this.contactInfoDetailsDto = contactInfoDetailsDto;
    }

    @PostMapping("")
    @Operation(summary = "Create a new Card", description = "Create a new card")
    @ApiResponse(responseCode = "201", description = "Card created successfully")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                  @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
                                                       String mobileNumber) {
        iCardService.createCard(mobileNumber);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED.value(), HttpStatus.CREATED.name()), HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    @Operation(summary = "Get customer and Account Details", description = "Get loan details using mobile number")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<CardDto> getCardUsingMobileNumber(@RequestHeader("X-Correlation-Id") String correlationId,
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
            String mobileNumber) {
        logger.debug("Card Controller :: Correlation ID: {}", correlationId);
        return new ResponseEntity<>(iCardService.getCardUsingMobileNumber(mobileNumber), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update Card details", description = "Update Card details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417",
                    description = "HTTP Status Expectation Failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardDto cardDto) {
        boolean updated = iCardService.updateCard(cardDto);
        if (!updated) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.name()), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name()), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete Card details", description = "Delete Card details using mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<ResponseDto> deleteLoan(
            @RequestParam
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number should be 10 digits")
            String mobileNumber) {
        boolean deleted = iCardService.deleteCardUsingMobileNumber(mobileNumber);
        if (!deleted) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.name()), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.name()), HttpStatus.OK);
    }

    @GetMapping("/build/version")
    @Operation(summary="Get Build Version", description = "Get Build Version")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<String> getBuildVersion() {
        return new ResponseEntity<>(buildVersion, HttpStatus.OK);
    }

    @GetMapping("/java/version")
    @Operation(summary="Get Java Version", description = "Get Java Version")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<String> getJavaVersion() {
        return new ResponseEntity<>(env.getProperty("JAVA_HOME"), HttpStatus.OK);
    }

    @GetMapping("/support-info")
    @Operation(summary="Get Support Contact Details", description="Get Support Contact details")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    public ResponseEntity<ContactInfoDetailsDto> getSupportContactDetails() {
        return new ResponseEntity<>(contactInfoDetailsDto, HttpStatus.OK);
    }
}
