package com.jay.AirBnb.Dto;

import com.jay.AirBnb.Entity.HotelEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private String type;
    private HotelEntity hotel;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private int totalCount;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
