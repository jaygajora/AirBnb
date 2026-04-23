package com.jay.AirBnb.Strategy;

import com.jay.AirBnb.Entity.InventoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Autowired
    public BigDecimal calculatePrice(InventoryEntity inventoryEntity){
        BigDecimal price = wrapped.calculatePrice(inventoryEntity);
        return price.multiply(inventoryEntity.getSurgeFactor());
    }
}
