package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.GuestDTO;

import java.util.List;

public interface GuestService {
    GuestDTO addGuest(GuestDTO guestDTO);
    GuestDTO updateGuest(Long guestId, GuestDTO guestDTO);
    GuestDTO getGuestById(Long guestId);
    List<GuestDTO> getAllGuests();
    String deleteGuest(Long guestId);

}
