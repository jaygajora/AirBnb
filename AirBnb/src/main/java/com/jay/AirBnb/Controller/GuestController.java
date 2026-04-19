package com.jay.AirBnb.Controller;


import com.jay.AirBnb.Dto.GuestDTO;
import com.jay.AirBnb.Service.Interface.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class GuestController {

    @Autowired
    GuestService guestService;

    @PostMapping("/guests")
    public ResponseEntity<GuestDTO> addGuest(@RequestBody GuestDTO guestDTO){
        return new ResponseEntity<>(guestService.addGuest(guestDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/guests/{guestId}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Long guestId, GuestDTO guestDTO){
        return new ResponseEntity<>(guestService.updateGuest(guestId, guestDTO), HttpStatus.OK);
    }

    @GetMapping("/guests/{guestId}")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable Long guestId){
        return new ResponseEntity<>(guestService.getGuestById(guestId), HttpStatus.OK);
    }

    @GetMapping("/guests")
    public ResponseEntity<List<GuestDTO>> getAllGuestsOfAUser(){
        return new ResponseEntity<>(guestService.getAllGuests(), HttpStatus.OK);
    }

    @DeleteMapping("/guests/{guestId}")
    public ResponseEntity<String> deleteGuest(@PathVariable Long guestId){
        return new ResponseEntity<>(guestService.deleteGuest(guestId), HttpStatus.OK);
    }
}

