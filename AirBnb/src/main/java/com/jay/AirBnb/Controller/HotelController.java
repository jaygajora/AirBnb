package com.jay.AirBnb.Controller;

import com.jay.AirBnb.Dto.HotelDTO;
import com.jay.AirBnb.Service.Interface.HotelService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO){
        return new ResponseEntity<>(hotelService.createHotel(hotelDTO), HttpStatus.CREATED);
    }

//    @PatchMapping("/hotel")
//    public ResponseEntity<HotelDTO> updateHotel(@RequestBody Long id){
//
//    }

    // Get this only to the admin of the hotel so that he/she could then edit details
    @GetMapping("/hotel/{id}")
    public ResponseEntity<HotelDTO> getHotelInfo(@PathVariable Long id){
        return new ResponseEntity<>(hotelService.getHotelInfo(id), HttpStatus.OK);
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id){
        return new ResponseEntity<>(hotelService.deleteHotel(id), HttpStatus.OK);
    }

    @GetMapping("/hotel")
    public ResponseEntity<List<HotelDTO>> getAllHotelsByOwner(){
        return new ResponseEntity<>(hotelService.getAllHotelsByOwner(), HttpStatus.OK);
    }

}
