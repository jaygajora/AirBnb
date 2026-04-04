package com.jay.AirBnb.Controller;

import com.jay.AirBnb.Dto.HotelDTO;
import com.jay.AirBnb.Service.Interface.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/hotel")
@RestController
public class HotelBrowseController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{id}/info")
    public ResponseEntity<HotelDTO> getHotelDetails(@PathVariable Long id){
        return new ResponseEntity<>(hotelService.getHotelDetails(id), HttpStatus.OK);
    }
}
