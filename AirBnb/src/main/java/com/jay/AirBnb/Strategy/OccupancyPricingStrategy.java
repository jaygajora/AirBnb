package com.jay.AirBnb.Strategy;

import com.jay.AirBnb.Entity.InventoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventoryEntity){

        BigDecimal price = wrapped.calculatePrice(inventoryEntity);

        double occupancy = (double) inventoryEntity.getBookCount() / inventoryEntity.getTotalCount();

        if(occupancy >= 0.8){
            price = price.multiply(BigDecimal.valueOf(1.2));
        }

        return price;
    }
}
