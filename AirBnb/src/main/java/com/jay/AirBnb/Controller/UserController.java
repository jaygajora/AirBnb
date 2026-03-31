package com.jay.AirBnb.Controller;

import com.jay.AirBnb.Dto.ProfileDTO;
import com.jay.AirBnb.Dto.UserDTO;
import com.jay.AirBnb.Service.Interface.UserService;
import com.jay.AirBnb.Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(){
        return new ResponseEntity<>(userService.getProfile(), HttpStatus.OK);
    }

}
