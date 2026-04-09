package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.HotelDTO;
import com.jay.AirBnb.Dto.RoomDTO;
import com.jay.AirBnb.Entity.HotelEntity;
import com.jay.AirBnb.Entity.RoomEntity;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Exceptions.UnauthorisedException;
import com.jay.AirBnb.Repository.HotelRepository;
import com.jay.AirBnb.Repository.RoomRepository;
import com.jay.AirBnb.Service.Interface.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImplementation implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RoomDTO addRoom(Long id, RoomDTO roomDTO){
        if(id == null){
            throw new IllegalArgumentException("Hotel Id cannot be EMPTY!");
        }

        HotelEntity hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No hotel found with id: " + id));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("You are not the owner of the hotel and hence you cannot add room in this hotel!");
        }

        String roomType = roomDTO.getType();
        BigDecimal basePrice = roomDTO.getBasePrice();
        int totalCount = roomDTO.getTotalCount();
        int capacity = roomDTO.getCapacity();

        if(roomType == null || roomType.trim().isEmpty()){
            throw new IllegalArgumentException("Room Type CANNOT be EMPTY!");
        }

        if(basePrice == null || basePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Base Price is INVALID! It should be greater than 0");
        }

        if(totalCount <= 0){
            throw new IllegalArgumentException("Total count of rooms CANNOT be less than ZERO");
        }

        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity of the room CANNOT be less than ZERO!");
        }

        RoomEntity rooms = modelMapper.map(roomDTO, RoomEntity.class);

        rooms.setHotel(hotel);

        roomRepository.save(rooms);

        return modelMapper.map(rooms, RoomDTO.class);
    }

    public RoomDTO getRoomByID(Long hotelId, Long roomId){
        if(hotelId == null){
            throw new IllegalArgumentException("Hotel Id CANNOT be NULL");
        }

        if(roomId == null){
            throw new IllegalArgumentException("Room Id CANNOT be EMPTY!");
        }

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("No hotel found with Hotel Id: " + hotelId));
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No room found with Rood id: " + roomId));

        if(!room.getHotel().equals(hotel)){
            throw new IllegalArgumentException("Hotel Id and Room Id do not MATCH!");
        }

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("You are not the Owner of the hotel and hence cannot access rooms of the hotel!");
        }

        return modelMapper.map(room, RoomDTO.class);
    }

    public RoomDTO updateRoom(Long hotelId, Long roomId, RoomDTO roomDTO){
        if(hotelId == null){
            throw new IllegalArgumentException("Hotel Id CANNOT be EMPTY!");
        }

        if(roomId == null){
            throw new IllegalArgumentException("Room Id CANNOT be EMPTY!");
        }

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("No hotel found with Hotel Id: " + hotelId));
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No room found with Room Id: " + roomId));

        if(!room.getHotel().equals(hotel)){
            throw new UnauthorisedException("This room" + roomId + "does not belong to this hotel: " + hotelId);
        }

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("You are not the owner of this hotel and hence you cannot this room");
        }

        String type = roomDTO.getType();
        BigDecimal basePrice = roomDTO.getBasePrice();
        int totalCount = roomDTO.getTotalCount();
        int capacity = roomDTO.getCapacity();

        if(type == null || type.trim().isEmpty()){
            throw new IllegalArgumentException("Room Type cannot be EMPTY!");
        }

        if(basePrice == null || basePrice.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Base Price of room cannot be EMPTY or less than equal to 0!");
        }

        if(totalCount <= 0){
            throw new IllegalArgumentException("Total Count of the rooms should be greater than 0");
        }

        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity of the room should be greater than 0");
        }

        room.setType(type);
        room.setBasePrice(basePrice);
        room.setTotalCount(totalCount);
        room.setCapacity(capacity);
        roomRepository.save(room);

        return modelMapper.map(room, RoomDTO.class);
    }

    public RoomDTO deleteRoomById(Long hotelId, Long roomId){
        if(hotelId == null){
            throw new IllegalArgumentException("Hotel Id CANNOT be NULL!");
        }

        if(roomId == null){
            throw new IllegalArgumentException("Room Id CANNOT be NULL!");
        }

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("No Hotel found with Hotel Id: " + hotelId));
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No Room found with Room Id: " + roomId));

        if(!room.getHotel().equals(hotel)){
            throw new UnauthorisedException("This room does not belongs to this hotel!");
        }

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("You are not the owner of this hotel and hence you cannot delete this room");
        }

        roomRepository.deleteById(roomId);

        return modelMapper.map(room, RoomDTO.class);
    }

    public List<RoomDTO> getAllRooms(Long hotelId) {
        if (hotelId == null) {
            throw new IllegalArgumentException("Hotel Id cannot be NULL!");
        }

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("No hotel found with Id: " + hotelId));

        return hotel.getRooms()
                .stream()
                .map((room) -> modelMapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }
}
