package com.restdocstoopenapiexample;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/path/{value}/variable")
    public ResponseEntity<ResponseForm> pathVariable(@PathVariable("value") String value) {
        if ("error".equals(value)) {
            throw new IllegalStateException("error value");
        }

        return ResponseEntity.ok(ResponseForm.builder()
                .message(value)
                .responseCode("Success")
                .build());
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseForm> post(@RequestBody @Valid RequestForm requestForm) {
        return ResponseEntity.ok(ResponseForm.builder()
                .message(requestForm.getName() + " " + requestForm.getValue())
                .responseCode("Success")
                .build());
    }
}
