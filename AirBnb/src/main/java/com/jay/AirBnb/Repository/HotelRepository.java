package com.jay.AirBnb.Repository;
import com.jay.AirBnb.Entity.HotelEntity;
import com.jay.AirBnb.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    List<HotelEntity> findByOwner(UserEntity user);
}
