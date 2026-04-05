package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.HotelDTO;
import com.jay.AirBnb.Entity.HotelContactInfo;
import com.jay.AirBnb.Entity.HotelEntity;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Exceptions.UnauthorisedException;
import com.jay.AirBnb.Repository.HotelRepository;
import com.jay.AirBnb.Service.Interface.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        HotelContactInfo contactInfo = hotelDTO.getContactInfo();

        if(name == null || name.trim().isEmpty()){
            throw new ResourceNotFoundException("Name cannot be EMPTY!");
        }

        if(city == null || city.trim().isEmpty()){
            throw new ResourceNotFoundException("City cannot be EMPTY!");
        }


        HotelEntity newHotel = modelMapper.map(hotelDTO, HotelEntity.class);

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        newHotel.setContactInfo(contactInfo);
        newHotel.setActive(false);
        newHotel.setOwner(user);

        hotelRepository.save(newHotel);

        return modelMapper.map(newHotel, HotelDTO.class);
    }

//    public HotelDTO updateHotel(HotelDTO hotelDTO){
//        String name = hotelDTO.getName();
//        String city = hotelDTO.getCity();
//    }


    public HotelDTO getHotelInfo(Long id){
        if(id == null){
            throw new ResourceNotFoundException("Hotel Id cannot be EMPTY!");
        }

        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Hotel found with Id: " + id));

        UserEntity loggedInUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!loggedInUser.equals(hotel.getOwner())){
            throw new UnauthorisedException("This information can only be retrieved by the owner of the hotel!");
        }

        return modelMapper.map(hotel, HotelDTO.class);
    }

    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO){
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }

        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No hotel found with Id: " + id));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new AccessDeniedException("You are not the owner of the hotel and hence you cannot edit the hotel");
        }

        String name = hotelDTO.getName();

        if(name != null && !name.trim().isEmpty() && !name.equals(hotel.getName())){
            hotel.setName(name);
        }

        String city = hotelDTO.getCity();

        if(city != null && !city.trim().isEmpty() && !city.equals(hotel.getCity())){
            hotel.setCity(city);
        }

        HotelContactInfo contactInfo = hotelDTO.getContactInfo();

        if(contactInfo != null){
//            String address = contactInfo.getAddress();
//            String location = contactInfo.getLocation();
//            String email = contactInfo.getEmail();
//            String phone = contactInfo.getPhoneNumber();

            hotel.setContactInfo(contactInfo);
        }

        Boolean active = hotelDTO.getActive();

        if(active !=  null && active != hotel.getActive()){
            hotel.setActive(active);
        }

        hotel = hotelRepository.save(hotel);

        return modelMapper.map(hotel, HotelDTO.class);
    }

    public String deleteHotel(Long id){
        if(id == null){
            throw new ResourceNotFoundException("Hotel Id required to delete the current hotel");
        }

        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No hotel found with id:" + id));

        UserEntity owner = hotel.getOwner();

        UserEntity loggedInUser =(UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!owner.equals(loggedInUser)){
            throw new UnauthorisedException("You are not the owner of the hotel and hence you are NOT allowed to DELETE this hotel");
        }

        hotelRepository.deleteById(id);

        return hotel.getName() + " DELETED SUCCESSFULLY";
    }

    public List<HotelDTO> getAllHotelsByOwner(){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<HotelEntity> hotels = hotelRepository.findByOwner(user);   // the repository/query will return a list of "HotelEnitiy" and NOT "HotelDTOs"

        // And hence we will need ot convert the whole list from "HotelEntity" to "HotelDTO"

        return hotels
                .stream()
                .map(hotel -> modelMapper.map(hotel, HotelDTO.class))
                .collect(Collectors.toList());


    }

    public HotelDTO toggleHotelActiveStatus(Long id){
        if(id == null){
            throw new ResourceNotFoundException("Hotel Id CANNOT be EMPTY!");
        }

        HotelEntity hotel = (HotelEntity) hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No hotel found with Id: " + id));

        UserEntity loggedInUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!loggedInUser.equals(hotel.getOwner())){
            throw new UnauthorisedException("You are not the OWNER of this hotel and hence you cannot toggle its ACTIVE status!");
        }

        Boolean active = hotel.getActive();

        hotel.setActive(!active);

        hotelRepository.save(hotel);

        return modelMapper.map(hotel, HotelDTO.class);
    }

    //public method for users
    public HotelDTO getHotelDetails(Long id){
        if(id == null){
            throw new ResourceNotFoundException("Hotel Id cannot be EMPTY!");
        }

        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Hotel found with Id: " + id));

        return modelMapper.map(hotel, HotelDTO.class);
    }


}
