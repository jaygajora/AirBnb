package com.jay.AirBnb.Dto;

import com.jay.AirBnb.Entity.HotelContactInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor     // WHY??????????????
@AllArgsConstructor
@Data
public class HotelDTO {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private Boolean active;
    private HotelContactInfo contactInfo;
//    private UserEntity owner;
}
