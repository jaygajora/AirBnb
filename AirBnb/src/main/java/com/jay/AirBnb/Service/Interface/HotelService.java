package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.HotelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HotelService {
    public HotelDTO createHotel(HotelDTO hotelDTO);
//    public HotelDTO updateHotel(HotelDTO hotelDTO);

    public HotelDTO getHotelInfo(Long id);

    public String deleteHotel(Long id);

    public List<HotelDTO> getAllHotelsByOwner();
    // public route for HOTEL BROWSING
    public HotelDTO getHotelDetails(Long id);
}
