package com.jay.AirBnb.Dto;

import com.jay.AirBnb.Entity.HotelEntity;
import com.jay.AirBnb.Enums.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BookingDTO {
    private Long id;
    private HotelEntity hotel;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private BookingStatus status;
    private Set<GuestDTO> guests;
    private BigDecimal amount;
}
