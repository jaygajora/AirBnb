package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.ProfileDTO;
import com.jay.AirBnb.Dto.UserDTO;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Repository.UserRepository;
import com.jay.AirBnb.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


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
}
