package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.HotelDTO;
import com.jay.AirBnb.Entity.HotelEntity;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Repository.HotelRepository;
import com.jay.AirBnb.Repository.UserRepository;
import com.jay.AirBnb.Service.Interface.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImplementation implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public HotelDTO createHotel(HotelDTO hotelDTO){
        String name = hotelDTO.getName();
        String city = hotelDTO.getCity();
        String[] photos = hotelDTO.getPhotos();
        String[] amenities = hotelDTO.getAmenities();

        if(name == null || name.trim().isEmpty()){
            throw new ResourceNotFoundException("Name cannot be EMPTY!");
        }

        if(city == null || city.trim().isEmpty()){
            throw new ResourceNotFoundException("City cannot be EMPTY!");
        }

        HotelEntity newHotel = modelMapper.map(hotelDTO, HotelEntity.class);

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        newHotel.setActive(false);
        newHotel.setOwner(user);

        hotelRepository.save(newHotel);

        return modelMapper.map(newHotel, HotelDTO.class);
    }
}
