package com.restdocstoopenapiexample;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class ResponseForm {
    @JsonProperty
    private final String message;
    @JsonProperty
    private final String responseCode;

    @Builder
    public ResponseForm(String message, String responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }
}
