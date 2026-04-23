package com.jay.AirBnb.Strategy;

import com.jay.AirBnb.Entity.InventoryEntity;

import java.math.BigDecimal;

public class BasePricingStrategy implements PricingStrategy{

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventoryEntity){
        return inventoryEntity.getRoom().getBasePrice();
    }
}
