package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.BookingDTO;
import com.jay.AirBnb.Dto.BookingRequest;
import com.jay.AirBnb.Dto.GuestDTO;
import com.jay.AirBnb.Enums.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingDTO initializeBooking(BookingRequest bookingRequest);
    BookingDTO addGuests(Long bookingId, List<GuestDTO> guests);
    BookingStatus getBookingStatusById(Long bookingId);
}
