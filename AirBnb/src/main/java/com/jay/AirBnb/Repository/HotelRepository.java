package com.jay.AirBnb.Repository;
import com.jay.AirBnb.Dto.HotelDTO;
import com.jay.AirBnb.Entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
