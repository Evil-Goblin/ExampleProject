package com.restdocstoopenapiexample;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestForm {
    @NotBlank
    private String name;
    @NotBlank
    private String value;

    @Builder
    public RequestForm(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
