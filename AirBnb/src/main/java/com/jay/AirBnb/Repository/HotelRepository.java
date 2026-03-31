package com.jay.AirBnb.Repository;
import com.jay.AirBnb.Entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
