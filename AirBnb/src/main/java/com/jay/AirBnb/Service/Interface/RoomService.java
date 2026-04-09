package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.RoomDTO;

import java.util.List;

public interface RoomService {
    public RoomDTO addRoom(Long id, RoomDTO roomDTO);
    public RoomDTO getRoomByID(Long hotelId, Long roomId);
    public RoomDTO updateRoom(Long hotelId, Long roomId, RoomDTO roomDTO);
    public RoomDTO deleteRoomById(Long hotelId, Long roomId);
    public List<RoomDTO> getAllRooms(Long hotelId);
}
