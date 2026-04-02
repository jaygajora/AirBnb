package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.ProfileUpdateDTO;
import com.jay.AirBnb.Dto.UserDTO;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Enums.Gender;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Exceptions.UnauthorisedException;
import com.jay.AirBnb.Repository.UserRepository;
import com.jay.AirBnb.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found found with Id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

    @Override
    public UserDTO getProfile(){

//        String email = SecurityContextHolder.getContext().getAuthentication().getName();

//        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("No user found with email: " + email));
//        System.out.println(user);

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(user, UserDTO.class);
    }


    @Override
    public String updateProfile(ProfileUpdateDTO profileUpdateDTO){
        String email = profileUpdateDTO.getEmail();
        String name = profileUpdateDTO.getFullName();
        LocalDate dateOfBirth = profileUpdateDTO.getDateOfBirth();
        Gender gender = profileUpdateDTO.getGender();

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(email == null){
            throw new ResourceNotFoundException("Email is required!");
        }

        if(user.getEmail().isEmpty()){
            throw new ResourceNotFoundException("Email not found in JWT");
        }

        if(!email.equals(user.getEmail())){
            throw new UnauthorisedException("Unauthorized to make changes to someone else's profile");
        }

        if(name != null && !name.trim().isEmpty()){
            user.setFullName(name);
        }

        if(dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Date should be in the PAST!");
        }
        else if(dateOfBirth != null){
            user.setDateOfBirth(dateOfBirth);
        }

        if(gender != null){
            user.setGender(gender);
        }


        userRepository.save(user);

        return "User Profile updated SUCCESSFULLY!";
    }
}
