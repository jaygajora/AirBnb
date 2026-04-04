package com.jay.AirBnb.Entity;

//package com.jay.AirBnb.Dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class HotelContactInfo {
    private String address;
    private String location;
    private String email;
    private String phoneNumber;
}
