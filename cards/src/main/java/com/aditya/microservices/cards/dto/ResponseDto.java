package com.aditya.microservices.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "Response", description = "Response")
@Data @AllArgsConstructor
public class ResponseDto {

    @Schema(name = "Status Code", example="200")
    private int statusCode;

    @Schema(name = "Status Message", example="OK")
    private String statusMessage;
}
