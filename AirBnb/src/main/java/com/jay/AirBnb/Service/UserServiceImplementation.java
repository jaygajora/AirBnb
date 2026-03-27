package com.jay.AirBnb.Service;

import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Repository.UserRepository;
import com.jay.AirBnb.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found found with Id: " + id));
    }
}
