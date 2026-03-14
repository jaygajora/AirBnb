package com.jay.AirBnb.Advice;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@Builder

public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private T data;
    private String message;
    private ApiError apiError;

    public ApiResponse(){
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data = data;
    }

    public ApiResponse(ApiError apiError){
        this();
        this.apiError = apiError;
    }

    public ApiResponse(HttpStatus status, T data, String message, ApiError apiError){
        this();
        this.status = status;
        this.data = data;
        this.message = message;
        this.apiError = apiError;
    }

}
