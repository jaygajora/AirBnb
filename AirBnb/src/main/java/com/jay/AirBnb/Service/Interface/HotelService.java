package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.HotelDTO;
import com.jay.AirBnb.Entity.HotelEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HotelService {
    public HotelDTO createHotel(HotelDTO hotelDTO);
//    public HotelDTO updateHotel(HotelDTO hotelDTO);

    public HotelDTO getHotelInfo(Long id);

    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO);

    public String deleteHotel(Long id);

    public List<HotelDTO> getAllHotelsByOwner();

    public HotelDTO toggleHotelActiveStatus(Long id);
    // public route for HOTEL BROWSING
    public HotelDTO getHotelDetails(Long id);
}
