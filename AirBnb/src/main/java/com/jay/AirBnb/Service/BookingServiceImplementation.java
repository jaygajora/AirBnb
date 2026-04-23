package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.BookingDTO;
import com.jay.AirBnb.Dto.BookingRequest;
import com.jay.AirBnb.Dto.GuestDTO;
import com.jay.AirBnb.Dto.InventoryDTO;
import com.jay.AirBnb.Entity.*;
import com.jay.AirBnb.Enums.BookingStatus;
import com.jay.AirBnb.Enums.Gender;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Exceptions.UnauthorisedException;
import com.jay.AirBnb.Repository.*;
import com.jay.AirBnb.Service.Interface.BookingService;
import com.jay.AirBnb.Strategy.PricingService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

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
    private GuestRepository guestRepository;

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
    @Override
    @Transactional
    public BookingDTO addGuests(Long bookingId, List<GuestDTO> guests){
        if(bookingId == null){
            throw new IllegalArgumentException("Booking Id CANNOT be EMPTY | NULL");
        }

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("No booking found with booking id: " + bookingId));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(booking.getUser())){
            throw new UnauthorisedException("You did not initialise this booking and hence you CANNOT ADD GUESTS to this booking");
        }

        if(hasBookingExpired(booking)){
           throw new IllegalStateException("Your booking window has expired. TRY AGAIN!");
        }

        if(booking.getStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Your booking is not under RESERVED state, you cannot add guests now! (Your booking has been confirmed or Cancelled)");
        }

        for(GuestDTO guestDTO : guests){
            String name = guestDTO.getName();
            int age = guestDTO.getAge();
            Gender gender = guestDTO.getGender();

            // if guestId == null, BUT what if the guest has been added just while making the booking!

            if(name == null || name.trim().isEmpty()){
                throw new IllegalArgumentException("Please enter the name of the guest!");
            }

            if(age < 0 || age > 120){
                throw new IllegalArgumentException("Age must be between 0 to 120");
            }

            if(gender == null){
                throw new IllegalArgumentException("Please select " + name + "'s gender!");
            }

            GuestEntity guest = modelMapper.map(guestDTO, GuestEntity.class);
            booking.setUser(user);     // isn't this reduntant as we are already setting the user while initializing the booking?
            guestRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setStatus(BookingStatus.GUESTS_ADDED);
        bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDTO.class);
    }

    // initiate Payments

    // capture payment

    // cancel booking

    // get Hotel Report

    // get booking status
    @Override
    public BookingStatus getBookingStatusById(Long bookingId){
        if(bookingId == null){
            throw new IllegalArgumentException("Booking id CANNOT be EMPTY | NULL");
        }

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("No booking found with booking id: " + bookingId));
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(booking.getUser())){
            throw new UnauthorisedException("This booking does not belong to you and hence you CANNOT access its BOOKING STATUS!");
        }

        BookingStatus status = booking.getStatus();

        if(status == BookingStatus.RESERVED || status == BookingStatus.GUESTS_ADDED){
            if(hasBookingExpired(booking)){
                throw new IllegalStateException("Your booking window has expired! Please TRY AGAIN!");
            }
        }

        return status;
    }

    // get All bookings by HotelId
    @Override
    public List<BookingDTO> getBookingsByHotelId(Long hotelId){
        if(hotelId == null){
            throw new IllegalArgumentException("Hotel id CANNOT be NULL | EMPTY!");
        }

        HotelEntity hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("No hotel found with hotel id: " + hotelId));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(hotel.getOwner())){
            throw new UnauthorisedException("You are not the owner of this hotel and hence you cannot retrieve the bookings of this hotel");
        }

        List<BookingEntity> bookingsByHotel = bookingRepository.findByHotel(hotel);

        return bookingsByHotel.stream()
                .map((bookingEntity) -> modelMapper.map(bookingEntity, BookingDTO.class))
                .collect(Collectors.toList());
    }

    // get my booking (user's booking/ bookingsByUser)
    @Override
    public List<BookingDTO> getBookingsByUser(Long userId){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<BookingEntity> bookingsByUser =  bookingRepository.findByUser(user);

        return bookingsByUser.stream()
                .map((bookingEntity) -> modelMapper.map(bookingEntity, BookingDTO.class))
                .collect(Collectors.toList());
    }

    //hasBookingExpired
    @Override
    public boolean hasBookingExpired(BookingEntity booking){
        return booking.getCreateAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

}
