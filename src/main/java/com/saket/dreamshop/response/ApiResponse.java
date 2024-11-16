package com.saket.dreamshop.response;

import lombok.Data;

@Data
public class ApiResponse {

    private final String message;
    private final Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
