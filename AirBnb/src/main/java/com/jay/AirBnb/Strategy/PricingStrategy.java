package com.jay.AirBnb.Strategy;

import com.jay.AirBnb.Entity.InventoryEntity;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(InventoryEntity inventoryEntity);
}
