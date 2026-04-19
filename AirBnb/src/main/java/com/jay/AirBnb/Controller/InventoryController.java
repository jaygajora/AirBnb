package com.jay.AirBnb.Controller;


import com.jay.AirBnb.Dto.InventoryDTO;
import com.jay.AirBnb.Dto.InventoryUpdateRequestDTO;
import com.jay.AirBnb.Repository.InventoryRepository;
import com.jay.AirBnb.Service.Interface.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<InventoryDTO>> getAllInventoryOfARoom(@PathVariable Long roomId){
        return new ResponseEntity<>(inventoryService.getAllInventoryByRoom(roomId), HttpStatus.OK);
    }

    @PatchMapping("/room/{roomId}")
    public ResponseEntity<String> updateInventory(@PathVariable Long roomId, @RequestBody InventoryUpdateRequestDTO newInventory){
        return new ResponseEntity<>(inventoryService.updateInventory(roomId, newInventory),HttpStatus.OK);
    }
}
