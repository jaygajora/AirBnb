package com.jay.AirBnb.Repository;

import com.jay.AirBnb.Dto.BookingDTO;
import com.jay.AirBnb.Entity.BookingEntity;
import com.jay.AirBnb.Entity.HotelEntity;
import com.jay.AirBnb.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByUser(UserEntity user);
    List<BookingEntity> findByHotel(HotelEntity hotel);

//    @Query("""
//        SELECT i
//            FROM Inventory i
//            WHERE hotelId = :hotelId
//            AND created BETWEEN :startDate AND :endDate
//        """)
    List<BookingEntity> findByHotelAndCreatedAtBetween(HotelEntity hotel, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
