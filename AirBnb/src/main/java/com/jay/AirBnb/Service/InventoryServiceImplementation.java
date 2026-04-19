package com.jay.AirBnb.Service;

import com.jay.AirBnb.Dto.InventoryDTO;
import com.jay.AirBnb.Dto.InventoryUpdateRequestDTO;
import com.jay.AirBnb.Entity.InventoryEntity;
import com.jay.AirBnb.Entity.RoomEntity;
import com.jay.AirBnb.Entity.UserEntity;
import com.jay.AirBnb.Exceptions.ResourceNotFoundException;
import com.jay.AirBnb.Exceptions.UnauthorisedException;
import com.jay.AirBnb.Repository.InventoryRepository;
import com.jay.AirBnb.Repository.RoomRepository;
import com.jay.AirBnb.Service.Interface.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImplementation implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public String initializeRoomForAYear(RoomEntity room){
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        for( ; !today.isAfter(endDate); today.plusDays(1)){
            InventoryEntity inventory = InventoryEntity.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .date(today)
                    .city(room.getHotel().getCity())
                    .bookCount(0)
                    .reservedCount(0)
                    .totalCount(room.getTotalCount())
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .closed(false)
                    .build();

            inventoryRepository.save(inventory);
        }

        return "Inventory of Room " + room.getId() + " in Hotel " + room.getHotel().getName() + " has been initialized for one year";
    }

    @Override
    public String deleteAllInventoriesOfARoom(RoomEntity room){
        inventoryRepository.deleteByRoom(room);

        return "Invetory for Room " + room.getId() + " of Hotel " + room.getHotel().getId() + " has been deleted!";
    }

    @Override
    public List<InventoryDTO> getAllInventoryByRoom(Long roomId){
        if(roomId == null){
            throw new IllegalArgumentException("RoomId cannot be NULL or EMPTY!");
        }

        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No room found with room id: " + roomId));

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!room.getHotel().getOwner().equals(user)){
            throw new UnauthorisedException("You are NOT the owner of this room/hotel");
        }

        return inventoryRepository.findByRoomOrderByDate(room).stream()
                .map((inventory) -> modelMapper.map(inventory, InventoryDTO.class))
                .collect(Collectors.toList());
    }

    // updateInventory()

    @Override
    @Transactional
    public String updateInventory(Long roomId, InventoryUpdateRequestDTO inventoryUpdateRequestDTO){
        if(roomId == null){
            throw new IllegalArgumentException("RoomId cannot bu NULL | EMPTY!");
        }

        RoomEntity room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("No room found with ID" + roomId));
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.equals(room.getHotel().getOwner())){
            throw new UnauthorisedException("You are not the owner of this hotel and hence cannot update the inventory for this room");
        }

        LocalDate startDate = inventoryUpdateRequestDTO.getStartDate();
        LocalDate endDate = inventoryUpdateRequestDTO.getEndDate();
        Boolean closed = inventoryUpdateRequestDTO.getClosed();
        BigDecimal surgeFactor = inventoryUpdateRequestDTO.getSurgeFactor();

        inventoryRepository.getInventoryAndLockBeforeUpdate(
                room.getId(),
                startDate,
                endDate
        );

        inventoryRepository.updateInventory(
                room.getId(),
                closed,
                surgeFactor,
                startDate,
                endDate
        );

        return "Inventory updated for roomId: " + roomId +
                " from " + startDate +
                " to " + endDate +
                " with closed set as " + closed +
                " and  surge factor set as " + surgeFactor;
    }



}
