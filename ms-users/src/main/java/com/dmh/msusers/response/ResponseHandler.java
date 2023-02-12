package com.dmh.msusers.response;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ResponseHandler {

    public ResponseEntity<Object> build(Object data, HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("status", status);
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }

}
