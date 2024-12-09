package com.restdocstoopenapiexample;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class ErrorResponse {
    @JsonProperty
    private final String error;
    @JsonProperty
    private final String message;
    @JsonProperty
    private final String responseCode;

    @Builder
    public ErrorResponse(String error, String message, String responseCode) {
        this.error = error;
        this.message = message;
        this.responseCode = responseCode;
    }
}
