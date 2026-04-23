package com.jay.AirBnb.Strategy;

import com.jay.AirBnb.Entity.InventoryEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(InventoryEntity inventoryEntity){
        PricingStrategy pricingStrategy = new BasePricingStrategy();

        // applying all the strategies
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventoryEntity);
    }

    public BigDecimal calculateTotalPrice(List<InventoryEntity> inventoryList){
        return inventoryList.stream()
                .map((inventory) -> calculateDynamicPricing(inventory))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
