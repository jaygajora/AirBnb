package com.jay.AirBnb.Strategy;

import com.jay.AirBnb.Entity.InventoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(InventoryEntity inventoryEntity){

        BigDecimal price = wrapped.calculatePrice(inventoryEntity);

        boolean isHoliday = true;   // call and API to check this

        if(isHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));
        }

        return price;
    }
}
