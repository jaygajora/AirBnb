package com.jay.AirBnb.Repository;

import com.jay.AirBnb.Dto.BookingDTO;
import com.jay.AirBnb.Entity.BookingEntity;
import com.jay.AirBnb.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByUser(UserEntity user);
}
