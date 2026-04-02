package com.jay.AirBnb.Controller;

import com.jay.AirBnb.Advice.ApiResponse;
import com.jay.AirBnb.Dto.ProfileUpdateDTO;
import com.jay.AirBnb.Dto.UserDTO;
import com.jay.AirBnb.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(){
        return new ResponseEntity<>(userService.getProfile(), HttpStatus.OK);
    }

    @PatchMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody ProfileUpdateDTO profileUpdateDTO){
        return new ResponseEntity<>(userService.updateProfile(profileUpdateDTO), HttpStatus.OK);
    }


}
