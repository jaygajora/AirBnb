package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.UserDTO;
import com.jay.AirBnb.Entity.UserEntity;

public interface UserService {

    public UserEntity getUserById(Long id);

    UserDTO getProfile();
}
