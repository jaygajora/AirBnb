package com.jay.AirBnb.Repository;

import com.jay.AirBnb.Dto.GuestDTO;
import com.jay.AirBnb.Entity.GuestEntity;
import com.jay.AirBnb.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
    List<GuestDTO> findByUser(UserEntity user);
}
