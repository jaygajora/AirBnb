package com.jay.AirBnb.Controller;


import com.jay.AirBnb.Dto.RoomDTO;
import com.jay.AirBnb.Entity.RoomEntity;
import com.jay.AirBnb.Service.Interface.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/admin/hotel/{hotelId}")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<RoomDTO> addRoom(@PathVariable Long hotelId, @RequestBody RoomDTO roomDTO){
        RoomDTO createdRoom = roomService.addRoom(hotelId, roomDTO);
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms(@PathVariable Long hotelId){
        List<RoomDTO> allRooms = roomService.getAllRooms(hotelId);
        return new ResponseEntity<>(allRooms, HttpStatus.OK);
    }

    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long hotelId, @PathVariable Long roomId, @RequestBody RoomDTO roomDTO){
        RoomDTO updatedRoom = roomService.updateRoom(hotelId, roomId, roomDTO);
        return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId){
        RoomDTO room = roomService.getRoomByID(hotelId, roomId);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<RoomDTO> deleteRoomById(@PathVariable Long hotelId, @PathVariable Long roomId){
        RoomDTO deletedRoom = roomService.deleteRoomById(hotelId, roomId);
        return new ResponseEntity<>(deletedRoom, HttpStatus.OK);
    }
}
