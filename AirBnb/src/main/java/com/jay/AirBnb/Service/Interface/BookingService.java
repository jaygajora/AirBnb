package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.BookingDTO;
import com.jay.AirBnb.Dto.BookingRequest;

public interface BookingService {
    BookingDTO initializeBooking(BookingRequest bookingRequest);
}
