package com.jay.AirBnb.Service.Interface;

import com.jay.AirBnb.Dto.InventoryDTO;
import com.jay.AirBnb.Dto.InventoryUpdateRequestDTO;
import com.jay.AirBnb.Entity.RoomEntity;

import java.util.List;


public interface InventoryService {
    String initializeRoomForAYear(RoomEntity room);
    String deleteAllInventoriesOfARoom(RoomEntity room);
    List<InventoryDTO> getAllInventoryByRoom(Long roomId);
    String updateInventory(Long roomId, InventoryUpdateRequestDTO newInventory);
}
