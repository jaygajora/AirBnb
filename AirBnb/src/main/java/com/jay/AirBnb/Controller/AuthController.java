package com.jay.AirBnb.Controller;


import com.jay.AirBnb.Advice.ApiResponse;
import com.jay.AirBnb.Dto.LoginDTO;
import com.jay.AirBnb.Dto.LoginResponseDTO;
import com.jay.AirBnb.Dto.SignUpRequestDTO;
import com.jay.AirBnb.Dto.UserDTO;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Repository.UserRepository;
import com.jay.AirBnb.Security.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO){
        return new ResponseEntity<>(authService.signUp(signUpRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response){

        String email = loginDTO.getEmail();

        if(email == null){
            throw new RuntimeException("Please enter your Email");
        }

        UserEntity user = userRepository.findByEmail(email.trim()).orElse(null);

        if(user == null){
            throw new ResourceNotFoundException("No such User found with email: " + email);
        }

        String[] tokens = authService.login(loginDTO);

        Cookie cookie = new Cookie("refreshToken", tokens[1]);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return new ResponseEntity<>(new LoginResponseDTO(tokens[0]), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token NOT found in Cookies"));

        String accessToken = authService.refreshTokens(refreshToken);

        return new ResponseEntity<>(new LoginResponseDTO(accessToken), HttpStatus.OK);
    }

}
