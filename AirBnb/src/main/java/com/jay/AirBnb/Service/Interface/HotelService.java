package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.HotelDTO;
import org.springframework.stereotype.Service;

public interface HotelService {
    public HotelDTO createHotel(HotelDTO hotelDTO);
}
