package com.jay.AirBnb.Security;

import com.jay.AirBnb.Dto.LoginDTO;
import com.jay.AirBnb.Dto.SignUpRequestDTO;
import com.jay.AirBnb.Dto.UserDTO;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Enums.Role;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;

    public UserDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        String name = signUpRequestDTO.getName();
        String email = signUpRequestDTO.getEmail();
        String password = signUpRequestDTO.getPassword();

        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Please enter your Name!");
        }

        if(email == null || email.trim().isEmpty()){
            throw new RuntimeException("Please enter your Email!");
        }

        if(password == null || password.trim().isEmpty()){
            throw new RuntimeException("Please enter your Password");
        }


        UserEntity userAlreadyExists = userRepository.findByEmail(email).orElse(null);

        if(userAlreadyExists != null){
            throw new RuntimeException("User with the same email ALREADY EXISTS!");
        }

        UserEntity newUser = modelMapper.map(signUpRequestDTO, UserEntity.class);

        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(password));

        UserEntity savedUser = userRepository.save(newUser);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    public String[] login(LoginDTO loginDTO){
        String username = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        if(username == null || username.trim().isEmpty()){
            throw new RuntimeException("Please enter your Username/Email!");
        }

        if(password == null || password.trim().isEmpty()){
            throw new RuntimeException("Please enter your Password");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password
        ));

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String[] accessAndRefreshTokens = new String[2];

        accessAndRefreshTokens[0] = jwtService.generateAccessToken(user);
        accessAndRefreshTokens[1] = jwtService.generateRefreshToken(user);

        return accessAndRefreshTokens;
    }

    public String refreshTokens(String token){
        Long id = jwtService.getUserIdFromToken(token);

        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found with id: " + id));

        return jwtService.generateAccessToken(user);
    }

}
