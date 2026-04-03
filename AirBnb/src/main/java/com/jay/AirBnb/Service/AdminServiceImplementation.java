package com.jay.AirBnb.Service;

import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Enums.Role;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Repository.UserRepository;
import com.jay.AirBnb.Service.Interface.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminServiceImplementation implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String setUserAsAdmin(String email){
        if(email == null || email.trim().isEmpty()){
            throw new ResourceNotFoundException("Email cannot be EMPTY!");
        }

        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("No user found with email: " + email));

        Set<Role> currRoleOfUser = user.getRoles();
        currRoleOfUser.add(Role.HOTEL_MANAGER);
        user.setRoles(currRoleOfUser);

        userRepository.save(user);

        return (user.getEmail()) + " is now set as an ADMIN";
    }
}
