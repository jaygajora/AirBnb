package com.jay.AirBnb.Advice;


import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder

public class ApiError {

    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

}
