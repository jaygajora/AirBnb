package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.BookingDTO;
import com.jay.AirBnb.Dto.BookingRequest;
import com.jay.AirBnb.Dto.InventoryDTO;
import com.jay.AirBnb.Entity.*;
import com.jay.AirBnb.Enums.BookingStatus;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Exceptions.UnauthorisedException;
import com.jay.AirBnb.Repository.BookingRepository;
import com.jay.AirBnb.Repository.HotelRepository;
import com.jay.AirBnb.Repository.InventoryRepository;
import com.jay.AirBnb.Repository.RoomRepository;
import com.jay.AirBnb.Service.Interface.BookingService;
import com.jay.AirBnb.Strategy.PricingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

@Service
public class BookingServiceImplementation implements BookingService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PricingService pricingService;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Override
    public BookingDTO initializeBooking(BookingRequest bookingRequest){

        Long hotelId = bookingRequest.getHotelId();

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("No Hotel Found with Hotel Id: " + hotelId));

        Long roomId = bookingRequest.getRoomId();
        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No room found with roomId: " + roomId));

        if(!hotel.equals(room.getHotel())){
            throw new UnauthorisedException("This room does not belong to this hotel");
        }

        LocalDate checkInDate = bookingRequest.getCheckInDate();
        LocalDate checkOutDate = bookingRequest.getCheckOutDate();

        Integer roomsCount = bookingRequest.getRoomsCount();

        List<InventoryEntity> inventoryList = inventoryRepository.findAndLockAvailableInventory(
                roomId,
                checkInDate,
                checkOutDate,
                roomsCount
        );

        long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate) + 1;

        if(inventoryList.size() != days){
            throw new IllegalStateException("Room is not available for the selected dates");
        }

        inventoryRepository.initBooking(
                roomId,
                checkInDate,
                checkOutDate,
                roomsCount
        );

        BigDecimal priceForOneRoom = pricingService.calculateTotalPrice(inventoryList);
        BigDecimal totalPrice = priceForOneRoom.multiply(BigDecimal.valueOf(roomsCount));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BookingEntity booking = BookingEntity.builder()
                    .status(BookingStatus.RESERVED)
                    .hotel(hotel)
                    .room(room)
                    .user(user)
                    .roomsCount(roomsCount)
                    .checkInDate(checkInDate)
                    .checkOutDate(checkOutDate)
                    .amount(totalPrice)
                    .build();

        bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDTO.class);

    }

    // add Guests

    // initiate Payments

    // capture payment

    // cancel booking

    // get booking status

    // get All bookigns by HotelId

    // get Hotel Report

    // get my booking (user's booking/ bookingsByUser)

    //hasBookingExpired



}
