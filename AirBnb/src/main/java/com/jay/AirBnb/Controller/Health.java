package com.jay.AirBnb.Controller;

import com.jay.AirBnb.Advice.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health {

    @GetMapping("/")
    public String home(){
        return "Hello! Welcome to Hotel Booking Website!";
    }

    @GetMapping("/health")
    public ApiResponse<Void> health(){
//        return "Hello! We are testing if the app is working or not!";
        return new ApiResponse<>(HttpStatus.OK, null, "Working fine! Health is Good!", null);
    }
}
